package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.domain.Pergunta;
import br.com.mackenzie.manager.potty.training.domain.Questionario;
import br.com.mackenzie.manager.potty.training.domain.Resposta;
import br.com.mackenzie.manager.potty.training.dto.PerguntaDTO;
import br.com.mackenzie.manager.potty.training.dto.QuestionarioDTO;
import br.com.mackenzie.manager.potty.training.dto.RespostaDTO;
import br.com.mackenzie.manager.potty.training.repository.QuestionarioRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class QuestionarioService {

    private final Log log = LogFactory.getLog(QuestionarioService.class);

    @Autowired
    private QuestionarioRepository questionarioRepository;

    @Autowired
    private ResponsavelService responsavelService;

    @Transactional
    public String save(QuestionarioDTO questionarioDTO, String token) {
        var questionario = Questionario.builder().
                descricao(questionarioDTO.getDescricao()).
                build();

        var perguntas = new ArrayList<Pergunta>();
        for (var perguntaDTO : questionarioDTO.getPerguntas()) {
            var pergunta = this.converter(perguntaDTO,questionario);
            perguntas.add(pergunta);
        }
        questionario.setPerguntas(perguntas);

        questionarioRepository.save(questionario);

        return "OK";
    }

    private Pergunta converter(PerguntaDTO perguntaDTO, Questionario questionario) {
        var pergunta = Pergunta.builder().
                descricao(perguntaDTO.getDescricao()).
                dica(perguntaDTO.getDica()).
                questionario(questionario).
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
                build();
    }

    public String obterQuestionarioPorToken(String token) {
        return null;
    }
}
