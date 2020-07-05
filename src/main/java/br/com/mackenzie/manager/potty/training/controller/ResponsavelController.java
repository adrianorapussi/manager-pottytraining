package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.config.JwtTokenUtil;
import br.com.mackenzie.manager.potty.training.dto.JwtRequest;
import br.com.mackenzie.manager.potty.training.dto.JwtResponse;
import br.com.mackenzie.manager.potty.training.dto.ResponsavelDTO;
import br.com.mackenzie.manager.potty.training.service.JwtUserDetailsService;
import br.com.mackenzie.manager.potty.training.service.ResponsavelService;
import br.com.mackenzie.manager.potty.training.util.TokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("responsavel")
@CrossOrigin
public class ResponsavelController {

    private final Log log = LogFactory.getLog(ResponsavelController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    private final ResponsavelService responsavelService;

    @Autowired
    public ResponsavelController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, ResponsavelService responsavelService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.responsavelService = responsavelService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            log.info("Login para " + jwtRequest);
            var authLogin = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
            authenticationManager.authenticate(authLogin);
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (DisabledException e) {
            log.error(e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.error(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody ResponsavelDTO responsavelDTO) throws Exception {
        log.info("Criando responsavel " + responsavelDTO);
        return ResponseEntity.ok(userDetailsService.save(responsavelDTO));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getResponsavelLogado(HttpServletRequest req) {
        log.info("Buscar Responsavel Logado ");
        String token = TokenUtil.extrairToken(req);
        return ResponseEntity.ok(responsavelService.buscarResponsavelLogado(token));
    }

}
