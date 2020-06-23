package br.com.mackenzie.manager.potty.training.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "crianca", schema = "potTrain")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Crianca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCrianca;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idResponsavel")
    @JsonBackReference
    private Responsavel responsavel;

    @Column
    private String dataNascimento;

    @Column
    private String utilizaFralda;

    @Column
    private String condicao;
}
