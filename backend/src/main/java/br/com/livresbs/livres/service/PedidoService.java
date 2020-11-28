package br.com.livresbs.livres.service;

import br.com.livresbs.livres.dto.*;
import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.model.StatusPedido;

public interface PedidoService {

    CheckoutDTO checkout(String cpfConsumidor);
    void salvarPedido(String cpfConsumidor, FinalizarPedidoDTO body);
    ItensDePedidoDTO consultarPedido(StatusPedido status);
    void avaliarPedido(Long idPedido, AvaliacaoPedidoDTO avaliacao);
}
