package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.dto.QuestionarioDTO;
import br.com.mackenzie.manager.potty.training.dto.RespostaSelecionadaDTO;
import br.com.mackenzie.manager.potty.training.service.QuestionarioService;
import br.com.mackenzie.manager.potty.training.util.TokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("questionario")
public class QuestionarioController {

    private final Log log = LogFactory.getLog(CriancaController.class);

    private final QuestionarioService questionarioService;

    @Autowired
    public QuestionarioController(QuestionarioService questionarioService) {
        this.questionarioService = questionarioService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarQuestionario(@RequestBody QuestionarioDTO questionarioDTO, HttpServletRequest req) {
        log.info("Criando questionario " + questionarioDTO);
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(questionarioService.save(questionarioDTO));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> obterQuestionarioPorToken(HttpServletRequest req) {
        log.info("obterCriancasDoResponsavel");
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(questionarioService.obterQuestionarioPorToken(token));
    }

    @RequestMapping(method = RequestMethod.POST, value = "resposta")
    public ResponseEntity<?> salvarResposta(@RequestBody RespostaSelecionadaDTO respostaSelecionadaDTO, HttpServletRequest req) {
        log.info("obterCriancasDoResponsavel");
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(questionarioService.salvarResposta(respostaSelecionadaDTO, token));
    }
}
