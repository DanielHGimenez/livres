package br.com.livresbs.livres.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItensDePedidoDTO {

    private List<PedidoDTO> pedidos;

}
