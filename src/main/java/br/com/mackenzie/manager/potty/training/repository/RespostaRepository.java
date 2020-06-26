package br.com.mackenzie.manager.potty.training.repository;

import br.com.mackenzie.manager.potty.training.domain.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Integer> {

}
