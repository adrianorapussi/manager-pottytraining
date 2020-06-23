package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.config.JwtTokenUtil;
import br.com.mackenzie.manager.potty.training.domain.Responsavel;
import br.com.mackenzie.manager.potty.training.repository.ResponsavelRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsavelService{

    private Log log = LogFactory.getLog(ResponsavelService.class);

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Responsavel buscarResponsavelLogado(String token) {
        var user = jwtTokenUtil.getUsernameFromToken(token);
        return responsavelRepository.findByPessoaNome(user);
    }
}
