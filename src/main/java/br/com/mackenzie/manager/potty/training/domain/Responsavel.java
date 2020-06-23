package br.com.mackenzie.manager.potty.training.domain;

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
@Table(name = "responsavel", schema = "potTrain")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Responsavel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idResponsavel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsavel")
    @JsonManagedReference
    private List<Crianca> criancas;

    @Column
    private Integer idPerguntaResposta;

    @Column
    private String estado;

    @Column
    private String cidade;

    @Column
    private String email;

    @Column
    private Timestamp dataCriacao;

    @Column
    private Timestamp dataAlteracao;

    @Column
    private String senha;

    @Column
    private String estadoCivil;

    @Column
    private String escolaridade;

    @Column
    private Boolean trabalhaFora;

}
