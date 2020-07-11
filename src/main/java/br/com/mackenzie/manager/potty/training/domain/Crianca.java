package br.com.mackenzie.manager.potty.training.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "crianca", schema = "potTrain")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Crianca implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crianca")
    @JsonManagedReference
    private List<Sessao> sessao;

    @Column
    private String dataNascimento;

    @Column
    private String utilizaFralda;

    @Column
    private String condicao;

    @Column
    private Timestamp dataCriacao;

    @Column
    private Timestamp dataAlteracao;
}
