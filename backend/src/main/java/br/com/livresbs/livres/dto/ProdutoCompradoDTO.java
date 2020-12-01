package br.com.livresbs.livres.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCompradoDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private Double quantidade;

}
