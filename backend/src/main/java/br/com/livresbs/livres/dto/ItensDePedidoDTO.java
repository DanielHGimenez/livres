package br.com.livresbs.livres.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class ItensDePedidoDTO {

    private List<PedidoDTO> pedidos;

}
