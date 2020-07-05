package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.domain.Pergunta;
import br.com.mackenzie.manager.potty.training.domain.Questionario;
import br.com.mackenzie.manager.potty.training.domain.Resposta;
import br.com.mackenzie.manager.potty.training.dto.PerguntaDTO;
import br.com.mackenzie.manager.potty.training.dto.QuestionarioDTO;
import br.com.mackenzie.manager.potty.training.dto.RespostaDTO;
import br.com.mackenzie.manager.potty.training.dto.RespostaSelecionadaDTO;
import br.com.mackenzie.manager.potty.training.repository.PerguntaRepository;
import br.com.mackenzie.manager.potty.training.repository.QuestionarioRepository;
import br.com.mackenzie.manager.potty.training.repository.RespostaRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionarioService {

    private final Log log = LogFactory.getLog(QuestionarioService.class);

    private final QuestionarioRepository questionarioRepository;

    private final ResponsavelService responsavelService;

    private final RespostaRepository respostaRepository;

    private final PerguntaRepository perguntaRepository;

    @Autowired
    public QuestionarioService(QuestionarioRepository questionarioRepository, ResponsavelService responsavelService, RespostaRepository respostaRepository, PerguntaRepository perguntaRepository) {
        this.questionarioRepository = questionarioRepository;
        this.responsavelService = responsavelService;
        this.respostaRepository = respostaRepository;
        this.perguntaRepository = perguntaRepository;
    }

    @Transactional
    public String save(QuestionarioDTO questionarioDTO) {
        var questionario = Questionario.builder().
                descricao(questionarioDTO.getDescricao()).
                preenchido(false).
                build();

        var perguntas = new ArrayList<Pergunta>();
        for (var perguntaDTO : questionarioDTO.getPerguntas()) {
            var pergunta = this.converter(perguntaDTO, questionario);
            perguntas.add(pergunta);
        }
        questionario.setPerguntas(perguntas);

        var responsaveis = responsavelService.buscarTodos();

        responsaveis.forEach(responsavel -> {
            questionario.setResponsavel(responsavel);
            questionarioRepository.save(questionario);
        });

        return "OK";
    }

    private Pergunta converter(PerguntaDTO perguntaDTO, Questionario questionario) {
        var pergunta = Pergunta.builder().
                descricao(perguntaDTO.getDescricao()).
                dica(perguntaDTO.getDica()).
                questionario(questionario).
                preenchida(false).
                build();

        var respostas = new ArrayList<Resposta>();
        for (var respostaDTO : perguntaDTO.getRespostas()) {
            var resposta = this.converter(respostaDTO);
            resposta.setPergunta(pergunta);
            respostas.add(resposta);
        }

        pergunta.setRespostas(respostas);
        return pergunta;
    }

    private Resposta converter(RespostaDTO respostaDTO) {
        return Resposta.builder().
                correta(respostaDTO.getCorreta()).
                descricao(respostaDTO.getDescricao()).
                selecionada(false).
                build();
    }

    @Transactional
    public List<Questionario> obterQuestionarioPorToken(String token) {
        var responsavel = responsavelService.buscarResponsavelLogado(token);

        return responsavel.getQuestionarios();
    }

    @Transactional
    public String salvarResposta(RespostaSelecionadaDTO respostaSelecionadaDTO, String token) {
        var responsavel = responsavelService.buscarResponsavelLogado(token);

        var questionarioOptional = responsavel.getQuestionarios().stream().filter(questionario ->
                questionario.getIdQuestionario().equals(respostaSelecionadaDTO.getIdQuestionatio())).
                findFirst();
        if (questionarioOptional.isEmpty()) {
            return "Questionario não encontrado";
        }

        var questionario = questionarioOptional.get();
        var perguntaOptional = questionario.getPerguntas().stream().filter(pergunta ->
                pergunta.getIdPergunta().equals(respostaSelecionadaDTO.getIdPergunta())).
                findFirst();

        if (perguntaOptional.isEmpty()) {
            return "Pergunta não encontrada";
        }

        var pergunta = perguntaOptional.get();
        pergunta.setPreenchida(true);
        pergunta.getRespostas().forEach(resposta -> resposta.setSelecionada(false));
        pergunta = perguntaRepository.save(pergunta);

        var respostaOptional = pergunta.getRespostas().stream().filter(resposta ->
                resposta.getIdResposta().equals(respostaSelecionadaDTO.getIdResposta())).
                findFirst();
        if (respostaOptional.isEmpty()) {
            return "Resposta não encontrada";
        }

        var resposta = respostaOptional.get();
        resposta.setSelecionada(true);
        respostaRepository.save(resposta);

        var perguntaPendentesStream = questionario.getPerguntas().stream().filter(perguntaPendente ->
                perguntaPendente.getPreenchida()==null || !perguntaPendente.getPreenchida());
        if (perguntaPendentesStream.count() == 0) {
            questionario.setPreenchido(true);
            questionarioRepository.save(questionario);
        }

        return "OK";
    }

    public List<Questionario> buscarTodosQuestionarios() {
        return questionarioRepository.findAll();
    }
}
