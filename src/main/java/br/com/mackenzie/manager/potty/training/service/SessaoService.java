package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.domain.Crianca;
import br.com.mackenzie.manager.potty.training.domain.Sessao;
import br.com.mackenzie.manager.potty.training.dto.SessaoDTO;
import br.com.mackenzie.manager.potty.training.repository.SessaoRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SessaoService {

    private final Log log = LogFactory.getLog(CriancaService.class);

    private final SessaoRepository sessaoRepository;

    private final ResponsavelService responsavelService;

    public SessaoService(SessaoRepository sessaoRepository, ResponsavelService responsavelService) {
        this.sessaoRepository = sessaoRepository;
        this.responsavelService = responsavelService;
    }

    public String iniciar(SessaoDTO sessaoDTO, String token) throws Exception {
        var responsavel = responsavelService.buscarResponsavelLogado(token);

        var crianca = buscarCriancaPorId(sessaoDTO, responsavel.getCriancas());

        var sessao = Sessao.builder().
                ativa(sessaoDTO.isIniciar()).
                dataInicio(new Timestamp(System.currentTimeMillis())).
                crianca(crianca).
                build();
        sessaoRepository.save(sessao);

        return "Iniciado";
    }

    public String terminar(SessaoDTO sessaoDTO, String token) throws Exception {
        Sessao sessao = obterSessaoPorIdCrianca(sessaoDTO, token);
        sessao.setAtiva(false);
        sessao.setDataInicio(new Timestamp(System.currentTimeMillis()));

        sessaoRepository.save(sessao);

        return "Encerrada sessao";
    }

    public String adicionarPontos(SessaoDTO sessaoDTO, String token) throws Exception {
        Sessao sessao = obterSessaoPorIdCrianca(sessaoDTO, token);
        sessao.setPontuacao(sessaoDTO.getPontos());

        sessaoRepository.save(sessao);

        return "Pontos adicionados";
    }

    private Sessao obterSessaoPorIdCrianca(SessaoDTO sessaoDTO, String token) throws Exception {
        var responsavel = responsavelService.buscarResponsavelLogado(token);

        var criancas = responsavel.getCriancas();
        if (criancas == null || criancas.isEmpty()) {
            throw new Exception("Responsavel não possui crianças");
        }

        var criancaAtual = buscarCriancaPorId(sessaoDTO, criancas);

        var sessaoOptional = criancaAtual.getSessao().stream().filter(Sessao::getAtiva).findFirst();
        if (sessaoOptional.isEmpty()) {
            throw new Exception("Crianca não possui sessao ativa");
        }
        return sessaoOptional.get();
    }

    private Crianca buscarCriancaPorId(SessaoDTO sessaoDTO, List<Crianca> criancas) throws Exception {
        var criancaAtual = criancas.stream().filter(crianca -> crianca.getIdCrianca().equals(sessaoDTO.getIdCrianca())).findFirst();
        if (criancaAtual.isEmpty()) {
            throw new Exception("Crianca nao encontrada");
        }
        return criancaAtual.get();
    }
}
