package br.com.mackenzie.manager.potty.training.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "questionario", schema = "potTrain")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Questionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idQuestionario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionario")
    @JsonManagedReference
    private List<Pergunta> perguntas;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idResponsavel", nullable = false)
    @JsonBackReference
    private Responsavel responsavel;

    @Column
    private String descricao;

    @Column
    private Boolean preenchido;

}
