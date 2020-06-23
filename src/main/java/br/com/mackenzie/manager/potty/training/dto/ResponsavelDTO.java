package br.com.mackenzie.manager.potty.training.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ResponsavelDTO implements Serializable {

    private String nome;
    private String idade;
    private String sexo;
    private String cidade;
    private String estadoCivil;
    private String escolaridade;
    private String trabalhaFora;
    private String senha;

}
