package br.com.livresbs.livres.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class EnderecoEntregaDTO {
    private Integer CEP;
    private String cidade;
    private String estado;
    private String endereco;
    private Integer numero;
    private String complemento;
}
