package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.config.properties.MessageProperty;
import br.com.livresbs.livres.dto.*;
import br.com.livresbs.livres.exception.CarrinhoVazioException;
import br.com.livresbs.livres.exception.LivresException;
import br.com.livresbs.livres.model.Carrinho;
import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.model.EnderecoEntrega;
import br.com.livresbs.livres.model.ItemPedido;
import br.com.livresbs.livres.model.MeioPagamento;
import br.com.livresbs.livres.model.MetodoPagamento;
import br.com.livresbs.livres.model.Pedido;
import br.com.livresbs.livres.model.StatusPedido;
import br.com.livresbs.livres.repository.CarrinhoRepository;
import br.com.livresbs.livres.repository.ConsumidorRepository;
import br.com.livresbs.livres.repository.EnderecoEntregaRepository;
import br.com.livresbs.livres.repository.ItemPedidoRepository;
import br.com.livresbs.livres.repository.MetodoPagamentoRepository;
import br.com.livresbs.livres.repository.PedidoRepository;
import br.com.livresbs.livres.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ConsumidorRepository consumidorRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private MessageProperty messageProperty;

    @Autowired
    private MetodoPagamentoRepository metodoPagamentoRepository;

    @Autowired
    private EnderecoEntregaRepository enderecoEntregaRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public CheckoutDTO checkout(String cpfConsumidor) {

        List<Carrinho> itemsCarrinho = carrinhoRepository.findByConsumidorCpf(cpfConsumidor);

        if (itemsCarrinho.isEmpty()) {
            throw new CarrinhoVazioException(messageProperty.getMenssagemCarrinhoVazio());
        }

        List<ProdutoCompradoDTO> produtos = new ArrayList<ProdutoCompradoDTO>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (Carrinho itemCarrinho : itemsCarrinho) {

            produtos.add(
                ProdutoCompradoDTO.builder()
                    .nome(itemCarrinho.getCotacao().getProduto().getNome())
                    .preco(itemCarrinho.getCotacao().getPreco())
                    .quantidade(itemCarrinho.getQuantidade().doubleValue())
                    .build()
            );

            valorTotal = valorTotal.add(
                    itemCarrinho.getCotacao().getPreco().multiply(
                            new BigDecimal(itemCarrinho.getQuantidade())
                    )
            );

        }

        List<MetodoPagamentoDTO> metodosPagamentoDTO = new ArrayList<MetodoPagamentoDTO>();

        metodoPagamentoRepository.findAll().forEach(metodoPagamento -> {
            metodosPagamentoDTO.add(
                MetodoPagamentoDTO.builder()
                    .nome(metodoPagamento.getNome())
                    .meiosPagamento(
                        metodoPagamento.getMeiosPagamento().stream().map(MeioPagamento::getNome).collect(Collectors.toList())
                    )
                    .build()
            );
        });

        return CheckoutDTO.builder()
                .produtos(produtos)
                .valorTotal(valorTotal)
                .metodosPagamento(metodosPagamentoDTO)
                .build();

    }

    @Override
    @Transactional
    public void salvarPedido(String cpfConsumidor, FinalizarPedidoDTO body) {
        EnderecoEntrega endereco = new EnderecoEntrega();
        endereco.setCEP(body.getCep());
        endereco.setCidade(body.getCidade());
        endereco.setEstado(body.getEstado());
        endereco.setEndereco(body.getEndereco());
        endereco.setComplemento(body.getEndereco());
        endereco.setNumero(body.getNumero());

        enderecoEntregaRepository.save(endereco);

        List<Carrinho> carrinhos = carrinhoRepository.findByConsumidorCpf(cpfConsumidor);
        Optional<Consumidor> consumidorOptional = consumidorRepository.findById(cpfConsumidor);
        Consumidor consumidor = consumidorOptional.get();
        consumidor.getEnderecos().add(endereco);
        consumidorRepository.save(consumidor);
        BigDecimal valorTotal = BigDecimal.ZERO;
        List<Carrinho> itemsCarrinho = carrinhoRepository.findByConsumidorCpf(cpfConsumidor);
        for (Carrinho itemCarrinho : itemsCarrinho) {
            valorTotal = valorTotal.add(
                    itemCarrinho.getCotacao().getPreco().multiply(
                            new BigDecimal(itemCarrinho.getQuantidade())
                    )
            );

        }

        MetodoPagamento metodoPagamento = metodoPagamentoRepository.findByNome(body.getMetodoPagamento()).get();

        Pedido pedido = new Pedido();
        pedido.setMetodoPagamento(metodoPagamento);
        pedido.setMeioPagamento(
            metodoPagamento.getMeiosPagamento()
                .stream()
                .filter(meioPagamento -> meioPagamento.getNome().equals(body.getMeioPagamento()))
                .collect(Collectors.toList())
                .get(0)
        );
        pedido.setValorTotal(valorTotal);
        pedido.setConsumidor(consumidor);
        pedido.setEnderecoEntrega(endereco);
        pedidoRepository.save(pedido);

        carrinhos.forEach(carrinho -> {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setCotacao(carrinho.getCotacao());
            itemPedido.setQuantidade(carrinho.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedidoRepository.save(itemPedido);
        });

        carrinhoRepository.deleteByConsumidorCpf(cpfConsumidor);
    }

    @Override
    public ItensDePedidoDTO consultarPedido(StatusPedido status){
        List<Pedido> pedidos = pedidoRepository.findByStatus(status);
        List<PedidoDTO> listaPedido = new LinkedList<PedidoDTO>();
        ConsumidorDTO consumidorDTO = null;
        List<ProdutoCompradoDTO> listaProdutos = new LinkedList<ProdutoCompradoDTO>();
        EnderecoEntregaDTO enderecoEntregaDTO = null;
        for (Pedido p: pedidos) {
            for (ItemPedido iten: p.getItemPedidos()) {
                ProdutoCompradoDTO produto = new ProdutoCompradoDTO();
                produto.setId(iten.getId());
                produto.setNome(iten.getCotacao().getProduto().getNome());
                produto.setPreco(iten.getCotacao().getProduto().getPreco());
                produto.setQuantidade(iten.getQuantidade());
                listaProdutos.add(produto);
            }
            consumidorDTO = ConsumidorDTO.builder()
                            .nome(p.getConsumidor().getNome() + " " + p.getConsumidor().getSobrenome())
                            .build();

            enderecoEntregaDTO = EnderecoEntregaDTO.builder()
                                .cidade(p.getEnderecoEntrega().getCidade())
                                .estado(p.getEnderecoEntrega().getEstado())
                                .CEP(p.getEnderecoEntrega().getCEP())
                                .endereco(p.getEnderecoEntrega().getEndereco())
                                .numero(p.getEnderecoEntrega().getNumero())
                                .complemento(p.getEnderecoEntrega().getComplemento())
                                .build();

            PedidoDTO pedidoDTO = PedidoDTO.builder()
                    .id(p.getId())
                    .consumidor(consumidorDTO)
                    .enderecoEntrega(enderecoEntregaDTO)
                    .metodoPagamento(p.getMetodoPagamento().getNome())
                    .meioPagamento(p.getMeioPagamento().getNome())
                    .produtos(listaProdutos)
                    .valorTotal(p.getValorTotal())
                    .build();

            listaPedido.add(pedidoDTO);
        }
        return ItensDePedidoDTO.builder().pedidos(listaPedido).build();
    }

    @Override
    public void avaliarPedido(Long idPedido, AvaliacaoPedidoDTO avaliacao) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new LivresException("pedido não achado"));

        if (avaliacao.getOperacao().equals(OperacaoAvaliacaoPedido.CANCELAR_PEDIDO)) {
            pedido.setStatus(StatusPedido.CANCELADO);
        }
        else {
            pedido.setStatus(StatusPedido.PENDENTE_ENTREGA);
            pedidoRepository.save(pedido);

            if (nonNull(avaliacao.getAlteracoes()))
                avaliacao.getAlteracoes().forEach((AlteracaoItemCarrinhoDTO alteracao) -> {
                    ItemPedido item = itemPedidoRepository.findById(alteracao.getId())
                            .orElseThrow(() -> new LivresException("item do pedido, não achado"));
                    item.setQuantidade(alteracao.getQuantidade());
                    itemPedidoRepository.save(item);
                });
        }
    }
}
