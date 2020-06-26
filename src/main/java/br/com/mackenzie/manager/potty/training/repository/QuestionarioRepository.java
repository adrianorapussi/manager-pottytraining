package br.com.mackenzie.manager.potty.training.repository;

import br.com.mackenzie.manager.potty.training.domain.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionarioRepository extends JpaRepository<Questionario, Integer> {

}
