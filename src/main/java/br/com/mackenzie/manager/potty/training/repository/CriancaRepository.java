package br.com.mackenzie.manager.potty.training.repository;

import br.com.mackenzie.manager.potty.training.domain.Crianca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriancaRepository extends JpaRepository<Crianca, Integer> {

    Crianca findByPessoaNome(String nome);

    List<Crianca> findByResponsavelIdResponsavel(Integer idResponsavel);
}
