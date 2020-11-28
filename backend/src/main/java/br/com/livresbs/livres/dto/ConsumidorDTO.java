package br.com.livresbs.livres.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ConsumidorDTO {
    private String nome;
    private String sobrenome;
    private String cpf;
    private String senha;
    private Long precomunidade;
}
