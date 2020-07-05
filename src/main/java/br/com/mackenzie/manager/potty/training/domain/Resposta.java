package br.com.mackenzie.manager.potty.training.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "resposta", schema = "potTrain")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idResposta;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPergunta", nullable = false)
    @JsonBackReference
    private Pergunta pergunta;

    @Column
    private String descricao;

    @Column
    private Boolean correta;

    @Column
    private Boolean selecionada;

}
