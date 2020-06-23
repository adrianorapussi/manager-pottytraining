package br.com.mackenzie.manager.potty.training.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CriancaDTO implements Serializable {

    private String nome;
    private String dataNascimento;
    private String sexo;
    private String idade;
    private String utilizaFralda;
    private String condicao;

}
