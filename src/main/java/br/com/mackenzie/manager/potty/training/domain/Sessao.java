package br.com.mackenzie.manager.potty.training.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "sessao", schema = "potTrain")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sessao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSessao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCrianca")
    @JsonBackReference
    private Crianca crianca;

    @Column
    private Boolean ativa;

    @Column
    private Timestamp dataInicio;

    @Column
    private Timestamp dataFim;

    @Column
    private Integer pontuacao;
}
