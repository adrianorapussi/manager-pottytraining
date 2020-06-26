package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.dto.QuestionarioDTO;
import br.com.mackenzie.manager.potty.training.service.QuestionarioService;
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

    private Log log = LogFactory.getLog(CriancaController.class);

    @Autowired
    private QuestionarioService questionarioService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarQuestionario(@RequestBody QuestionarioDTO questionarioDTO, HttpServletRequest req) throws Exception {
        log.info("Criando questionario "+questionarioDTO);
        String token = req.getHeader("Authorization").split(" ")[1];
        return ResponseEntity.ok(questionarioService.save(questionarioDTO,token));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> obterQuestionarioPorToken(HttpServletRequest req) throws Exception {
        log.info("obterCriancasDoResponsavel");
        String token = req.getHeader("Authorization").split(" ")[1];
        return ResponseEntity.ok(questionarioService.obterQuestionarioPorToken(token));
    }
}
