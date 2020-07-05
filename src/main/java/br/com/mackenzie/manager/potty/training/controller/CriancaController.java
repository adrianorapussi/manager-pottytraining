package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.dto.CriancaDTO;
import br.com.mackenzie.manager.potty.training.service.CriancaService;
import br.com.mackenzie.manager.potty.training.util.TokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("crianca")
@CrossOrigin
public class CriancaController {

    private final Log log = LogFactory.getLog(CriancaController.class);

    private final CriancaService criancaService;

    @Autowired
    public CriancaController(CriancaService criancaService) {
        this.criancaService = criancaService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody CriancaDTO criancaDTO, HttpServletRequest req) {
        log.info("Criando crianca " + criancaDTO);
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(criancaService.save(criancaDTO, token));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> obterCriancasDoResponsavel(HttpServletRequest req) {
        log.info("obterCriancasDoResponsavel");
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(criancaService.obterCriancasDoResponsavel(token));
    }
}
