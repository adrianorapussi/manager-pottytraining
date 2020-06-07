package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.domain.Pessoa;
import br.com.mackenzie.manager.potty.training.domain.Responsavel;
import br.com.mackenzie.manager.potty.training.dto.UserDTO;
import br.com.mackenzie.manager.potty.training.repository.PessoaRepository;
import br.com.mackenzie.manager.potty.training.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var responsavel = responsavelRepository.findByUsuario(username);
            return new User(responsavel.getUsuario(), responsavel.getSenha(), new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public Object save(UserDTO user) throws Exception {
        var responsavel = responsavelRepository.findByUsuario(user.getUsername());
        if (responsavel == null) {
            responsavel = Responsavel.builder().
                    pessoa(Pessoa.builder().
                            nome(user.getUsername()).
                            build()).
                    usuario(user.getUsername()).
                    senha(bcryptEncoder.encode(user.getPassword())).
                    dataCriacao(new Timestamp(System.currentTimeMillis())).
                    dataAlteracao(new Timestamp(System.currentTimeMillis())).
                    idPerguntaResposta(1).
                    idResponsavel(1).
                    build();
            responsavelRepository.save(responsavel);
            return "Usuario salvo com sucesso";
        } else {
            return "Usuario já existe";
        }

    }

}
