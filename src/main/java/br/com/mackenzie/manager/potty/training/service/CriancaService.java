package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.domain.Crianca;
import br.com.mackenzie.manager.potty.training.domain.Pessoa;
import br.com.mackenzie.manager.potty.training.dto.CriancaDTO;
import br.com.mackenzie.manager.potty.training.repository.CriancaRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriancaService {

    private final Log log = LogFactory.getLog(CriancaService.class);

    private final CriancaRepository criancaRepository;

    private final ResponsavelService responsavelService;

    @Autowired
    public CriancaService(CriancaRepository criancaRepository, ResponsavelService responsavelService) {
        this.criancaRepository = criancaRepository;
        this.responsavelService = responsavelService;
    }

    public String save(CriancaDTO criancaDTO, String token) {

        var responsavel = responsavelService.buscarResponsavelLogado(token);

        var criancaNova = Crianca.builder().
                pessoa(Pessoa.builder().
                        nome(criancaDTO.getNome()).
                        idade(Integer.parseInt(criancaDTO.getIdade())).
                        sexo(criancaDTO.getSexo()).
                        build()).
                condicao(criancaDTO.getCondicao()).
                dataNascimento(criancaDTO.getDataNascimento()).
                responsavel(responsavel).
                utilizaFralda(criancaDTO.getUtilizaFralda()).
                build();

        criancaRepository.save(criancaNova);
        return "crianca salva";
    }

    public List<Crianca> obterCriancasDoResponsavel(String token) {
        var responsavel = responsavelService.buscarResponsavelLogado(token);
        return criancaRepository.findByResponsavelIdResponsavel(responsavel.getIdResponsavel());
    }
}
