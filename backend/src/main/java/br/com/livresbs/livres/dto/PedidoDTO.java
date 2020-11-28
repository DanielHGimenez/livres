package br.com.livresbs.livres.dto;

import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.model.Pedido;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class PedidoDTO {

    private BigDecimal valorTotal;
    private ConsumidorDTO consumidor;
    private List<ProdutoCompradoDTO> produtos;
    private MetodoPagamentoDTO metodoPagamento;
    private EnderecoEntregaDTO enderecoEntrega;

}
