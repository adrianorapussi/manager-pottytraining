package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.config.JwtTokenUtil;
import br.com.mackenzie.manager.potty.training.domain.Responsavel;
import br.com.mackenzie.manager.potty.training.repository.ResponsavelRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsavelService {

    private final Log log = LogFactory.getLog(ResponsavelService.class);

    private final ResponsavelRepository responsavelRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ResponsavelService(ResponsavelRepository responsavelRepository, JwtTokenUtil jwtTokenUtil) {
        this.responsavelRepository = responsavelRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Responsavel buscarResponsavelLogado(String token) {
        log.info("Buscando usuario logado");
        var user = jwtTokenUtil.getUsernameFromToken(token);
        return responsavelRepository.findByPessoaNome(user);
    }

    public List<Responsavel> buscarTodos() {
        log.info("Buscando todos os responsaveis");
        return responsavelRepository.findAll();
    }

    public void save(Responsavel responsavel) {
        responsavelRepository.save(responsavel);
    }
}
