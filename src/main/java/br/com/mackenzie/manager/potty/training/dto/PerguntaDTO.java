package br.com.mackenzie.manager.potty.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerguntaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descricao;

    private List<RespostaDTO> respostas;

    private String dica;

}
