package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.dto.SessaoDTO;
import br.com.mackenzie.manager.potty.training.service.SessaoService;
import br.com.mackenzie.manager.potty.training.util.TokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("sessao")
@CrossOrigin
public class SessaoController {

    private final Log log = LogFactory.getLog(CriancaController.class);

    private final SessaoService sessaoService;

    @Autowired
    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "ativa")
    public ResponseEntity<?> ativar(@RequestBody SessaoDTO sessaoDTO, HttpServletRequest req) throws Exception {
        log.info("Iniciando sessao " + sessaoDTO);
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(sessaoService.iniciar(sessaoDTO, token));
    }

    @RequestMapping(method = RequestMethod.POST, value = "desativa")
    public ResponseEntity<?> desativar(@RequestBody SessaoDTO sessaoDTO, HttpServletRequest req) throws Exception {
        log.info("Terminando sessao " + sessaoDTO);
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(sessaoService.terminar(sessaoDTO, token));
    }

    @RequestMapping(method = RequestMethod.POST, value = "ponto")
    public ResponseEntity<?> adicionarPonto(@RequestBody SessaoDTO sessaoDTO, HttpServletRequest req) throws Exception {
        log.info("Adicao ponto na sessao " + sessaoDTO);
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(sessaoService.adicionarPontos(sessaoDTO, token));
    }
}
