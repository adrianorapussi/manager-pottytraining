package br.com.mackenzie.manager.potty.training.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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
    @JoinColumn(name = "idPessoa", referencedColumnName = "idPessoa")
    private Pessoa pessoa;

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
    private String usuario;

    @Column
    private String senha;

}
