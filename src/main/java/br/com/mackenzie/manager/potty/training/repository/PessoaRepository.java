package br.com.mackenzie.manager.potty.training.repository;

import br.com.mackenzie.manager.potty.training.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
