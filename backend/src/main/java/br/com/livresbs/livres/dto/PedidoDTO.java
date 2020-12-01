package br.com.livresbs.livres.dto;

import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private BigDecimal valorTotal;
    private ConsumidorDTO consumidor;
    private List<ProdutoCompradoDTO> produtos;
    private String metodoPagamento;
    private String meioPagamento;
    private EnderecoEntregaDTO enderecoEntrega;

}
