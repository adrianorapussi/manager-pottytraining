package br.com.mackenzie.manager.potty.training.controller;

import br.com.mackenzie.manager.potty.training.repository.PessoaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/pessoa")
public class PessoaController {

    private PessoaRepository pessoaRepository;

    @GetMapping("")
    public ResponseEntity all() {
        return ok(this.pessoaRepository.findAll());
    }
}
