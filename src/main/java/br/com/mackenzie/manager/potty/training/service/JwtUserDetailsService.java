package br.com.mackenzie.manager.potty.training.service;

import br.com.mackenzie.manager.potty.training.domain.Pessoa;
import br.com.mackenzie.manager.potty.training.domain.Responsavel;
import br.com.mackenzie.manager.potty.training.dto.ResponsavelDTO;
import br.com.mackenzie.manager.potty.training.dto.UserDTO;
import br.com.mackenzie.manager.potty.training.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var responsavel = responsavelRepository.findByPessoaNome(username);
            return new User(responsavel.getPessoa().getNome(), responsavel.getSenha(), new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public String save(UserDTO user) throws Exception {
        var responsavel = responsavelRepository.findByPessoaNome(user.getUsername());
        if (responsavel == null) {
            responsavel = Responsavel.builder().
                    pessoa(Pessoa.builder().
                            nome(user.getUsername()).
                            build()).
                    senha(bcryptEncoder.encode(user.getPassword())).
                    dataCriacao(new Timestamp(System.currentTimeMillis())).
                    dataAlteracao(new Timestamp(System.currentTimeMillis())).
                    build();
            responsavelRepository.save(responsavel);
            return "Usuario salvo com sucesso";
        } else {
            return "Usuario já existe";
        }
    }

    public String save(ResponsavelDTO responsavelDTO) throws Exception {
        try {
            var responsavel = responsavelRepository.findByPessoaNome(responsavelDTO.getNome());
            if (responsavel != null) {
                return "Usuário já consta no banco";
            }
            responsavel = Responsavel.builder().
                    pessoa(Pessoa.builder().
                            nome(responsavelDTO.getNome()).
                            sexo(responsavelDTO.getSexo()).
                            idade(Integer.parseInt(responsavelDTO.getIdade())).
                            build()).
                    senha(bcryptEncoder.encode(responsavelDTO.getSenha())).
                    dataCriacao(new Timestamp(System.currentTimeMillis())).
                    dataAlteracao(new Timestamp(System.currentTimeMillis())).
                    cidade(responsavelDTO.getCidade()).
                    estadoCivil(responsavelDTO.getEstadoCivil()).
                    trabalhaFora("SIM".equalsIgnoreCase(responsavelDTO.getTrabalhaFora())).
                    escolaridade(responsavelDTO.getEscolaridade()).
                    build();
            responsavelRepository.save(responsavel);
            return "Usuario salvo com sucesso";

        } catch (Exception e) {
            throw new SystemException("Problema ao salvar usuário");
        }
    }

    public String getUserFromContext() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

}
