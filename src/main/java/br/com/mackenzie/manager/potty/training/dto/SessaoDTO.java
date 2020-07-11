package br.com.mackenzie.manager.potty.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean iniciar;

    private boolean terminar;

    private Integer idCrianca;

    private Integer pontos;

}
