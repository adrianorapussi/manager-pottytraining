package br.com.mackenzie.manager.potty.training.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}