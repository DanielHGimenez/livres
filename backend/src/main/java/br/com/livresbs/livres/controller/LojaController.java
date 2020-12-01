package br.com.livresbs.livres.controller;

import br.com.livresbs.livres.dto.*;
import br.com.livresbs.livres.model.StatusPedido;
import br.com.livresbs.livres.service.CarrinhoService;
import br.com.livresbs.livres.service.LojaService;
import br.com.livresbs.livres.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/loja")
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/produtos")
    public ProdutosDisponiveisDTO getProdutosDisponiveisVendaByCategoria (
            @RequestHeader(value = "cpf") String cpf,
            @RequestParam(value = "pagina", required = false) Integer numeroPagina,
            @RequestParam(value = "categorias", required = false) List<String> categorias
    ) {

        if (numeroPagina == null || numeroPagina < 1)
            numeroPagina = 1;

        return lojaService.listarProdutosDisponiveisCompraConsumidor(cpf, numeroPagina, categorias);

    }

    @PostMapping("/carrinhos")
    public void sincronizarProduto(
            @RequestHeader(value = "cpf") String cpf,
            @Valid @RequestBody ProdutoCarrinhoDTO produtoCarrinhoDTO
    ) {
        carrinhoService.sincronizarProduto(
                cpf,
                produtoCarrinhoDTO.getCotacaoId(),
                produtoCarrinhoDTO.getQuantidade()
        );
    }

    @GetMapping("/carrinhos")
    public CarrinhoDTO listarCarrinhos(
            @RequestHeader(value = "cpf") String cpf
    ) {

        CarrinhoDTO carrinhoDTO = carrinhoService.listarCarrinhos(cpf);
        return carrinhoDTO;

    }

    @GetMapping("/pedidos")
    public CheckoutDTO checkout(
            @RequestHeader(value = "cpf") String cpf) {

        return pedidoService.checkout(cpf);
    }

    @PostMapping("/pedidos")
    public void salvarPedido(@RequestHeader(value = "cpf") String cpf, @RequestBody FinalizarPedidoDTO body){
        pedidoService.salvarPedido(cpf, body);
    }

    @GetMapping("/pedidos/salvos")
    public ItensDePedidoDTO consultarPedidos(
            @RequestParam(value = "status") StatusPedido status){

        return  pedidoService.consultarPedido(status);
    }

    @PutMapping("/pedidos/{idPedido}/")
    public void salvarResultadoAvaliacao(@PathVariable Long idPedido, @RequestBody @Valid AvaliacaoPedidoDTO body) {
        pedidoService.avaliarPedido(idPedido, body);
    }

    
}
