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
@Table(name = "pergunta", schema = "potTrain")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pergunta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPergunta;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idQuestionario", nullable = false)
    @JsonBackReference
    private Questionario questionario;

    @Column
    private String descricao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pergunta")
    @JsonManagedReference
    private List<Resposta> respostas;

    @Column
    private String dica;

    @Column
    private Boolean preenchida;

}
