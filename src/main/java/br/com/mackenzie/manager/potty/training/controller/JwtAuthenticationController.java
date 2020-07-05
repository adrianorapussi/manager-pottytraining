package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.config.JwtTokenUtil;
import br.com.mackenzie.manager.potty.training.dto.JwtRequest;
import br.com.mackenzie.manager.potty.training.dto.JwtResponse;
import br.com.mackenzie.manager.potty.training.dto.UserDTO;
import br.com.mackenzie.manager.potty.training.service.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

//@RestController
//@CrossOrigin
public class JwtAuthenticationController {

    //@Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    private JwtTokenUtil jwtTokenUtil;

    //@Autowired
    private JwtUserDetailsService userDetailsService;

    //@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    //@RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}