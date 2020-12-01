<template>
  <div>
    <div
      class="modal fade"
      id="modalDetalhesPedido"
      tabindex="-1"
      role="dialog"
    >
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Detalhes do Pedido</h5>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-12">
                <label
                  ><strong>Consumidor:</strong>
                  {{ selected.consumidor.nome }}</label
                >
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <label
                  ><strong>CEP:</strong>
                  {{ selected.enderecoEntrega.cep }}</label
                >
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <label
                  ><strong>Cidade:</strong>
                  {{ selected.enderecoEntrega.cidade }}/{{
                    selected.enderecoEntrega.estado
                  }}</label
                >
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <label
                  ><strong>Endereço:</strong>
                  {{ selected.enderecoEntrega.endereco }}</label
                >
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <label
                  ><strong>Método de Pgto.:</strong>
                  {{ selected.metodoPagamento }}</label
                >
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <label><strong>Produtos:</strong></label>
                <div class="table-responsive">
                  <table
                    id="dtBasicExample"
                    class="table table-striped table-bordered table-sm"
                    cellspacing="0"
                    width="100%"
                  >
                    <thead class="thead-dark" style="vertical-align: middle">
                      <tr>
                        <th class="th-sm">Nome</th>
                        <!-- <th class="th-sm">Preço Un.</th> -->
                        <th class="th-sm text-right">Qtd.</th>
                        <th class="th-sm text-right">Preço UN.</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="pedido in selected.produtos" :key="pedido.id">
                        <td class="">{{ pedido.nome }}</td>
                        <!-- <td class="">{{ pedido.precoUN }}</td> -->
                        <td class="text-right w-25">
                          <input
                            class="form-control form-control-sm text-right"
                            autocomplete="off"
                            type="number"
                            min="0"
                            value="0"
                            v-model="pedido.quantidade"
                            @change="pedidoAlterado(pedido)"
                          />
                        </td>
                        <td class="text-right w-25">{{ pedido.precoView }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <label style="font-size: 1.25em"
                  ><strong>Total do Pedido:</strong>
                  {{ selected.valorTotalView }}</label
                >
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-primary"
              data-dismiss="modal"
              @click="fecharDetalhes(selected)"
            >
              Fechar
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-8 col-sm-12 offset-md-2">
        <div class="card">
          <div class="card-header">Pedidos para Aprovação</div>
          <div class="card-body">
            <div v-show="!pedidos.length">
              <h5 class="text-center">{{ info }}</h5>
            </div>

            <div class="table-responsive" v-show="pedidos.length">
              <table
                id="dtBasicExample"
                class="table table-striped table-bordered table-sm"
                cellspacing="0"
                width="100%"
              >
                <thead class="thead-dark" style="vertical-align: middle">
                  <tr>
                    <th class="th-sm">Consumidor</th>
                    <th class="th-sm text-right">Qtd. Produtos</th>
                    <th class="th-sm text-right">Total (R$)</th>
                    <th class="text-center" width="200px">Ação</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="pedido in pedidos" :key="pedido.id">
                    <td>{{ pedido.consumidor.nome }}</td>
                    <td class="text-right">{{ pedido.produtos.length }}</td>
                    <td class="text-right">{{ pedido.valorTotalView }}</td>
                    <td class="text-center btns-acoes">
                      <button
                        class="btn btn-sm btn-warning"
                        data-toggle="modal"
                        data-target="#modalDetalhesPedido"
                        @click="selectPedido(pedido)"
                      >
                        <i class="fa fa-search" aria-hidden="true"></i>
                      </button>
                      <button
                        class="btn btn-sm btn-success"
                        @click="aprovarPedido(pedido)"
                      >
                        <i class="fa fa-check-square" aria-hidden="true"></i>
                      </button>
                      <button
                        class="btn btn-sm btn-danger"
                        @click="recusarPedido(pedido)"
                      >
                        <i class="fa fa-times" aria-hidden="true"></i>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
strong {
  font-weight: bold;
}

.btns-acoes button:nth-child(1) {
  margin: 0 2px 0 0;
  -webkit-box-shadow: initial;
}

.btns-acoes button:nth-child(2) {
  margin: 0 2px 0 2px;
  -webkit-box-shadow: initial;
}

.btns-acoes button:nth-child(3) {
  margin: 0 0 0 2px;
  -webkit-box-shadow: initial;
}
</style>
<script>
import Vue from "vue";
import loja from "../services/loja";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      dialog: false,
      info: "Carregando...",
      selected: {
        consumidor: {},
        enderecoEntrega: {},
      },
      pedidos: [],
    };
  },
  created() {
    const that = this;
    loja.consultarPedidos("CRIADO").then((res) => {
      that.pedidos = res.data.pedidos || [];

      if (!that.pedidos.length)
        this.info = "Não há pedidos para serem Avaliados";

      that.pedidos.map((p) => {
        p.valorTotalView =
          "R$ " +
          p.valorTotal.toLocaleString("pt-BR", {
            maximumFractionDigits: 2,
            minimumFractionDigits: 2,
          });

        p.produtos.map((p) => {
          /* p.precoUN = p.preco / p.quantidade;

          p.precoUN =
            "R$ " +
            p.precoUN.toLocaleString("pt-BR", {
              maximumFractionDigits: 2,
              minimumFractionDigits: 2,
            });*/

          p.precoView =
            "R$ " +
            p.preco.toLocaleString("pt-BR", {
              maximumFractionDigits: 2,
              minimumFractionDigits: 2,
            });
        });
      });
    });
  },
  methods: {
    selectPedido(pedido) {
      Vue.set(this.$data, "selected", JSON.parse(JSON.stringify(pedido)));
    },

    pedidoAlterado(pedido) {
      this.selected.alterado = true;
      pedido.alterado = true;
      this.selected.valorTotal = this.selected.produtos.reduce(
        (a, b) => a + b.preco * parseInt(b.quantidade),
        0
      );
      this.selected.valorTotalView =
        "R$ " +
        this.selected.valorTotal.toLocaleString("pt-BR", {
          maximumFractionDigits: 2,
          minimumFractionDigits: 2,
        });
    },

    fecharDetalhes(pedido) {
      const that = this;
      if (pedido.alterado) {
        Swal.fire({
          title: "Confirmação:<br>Alterações do Pedido",
          showDenyButton: true,
          icon: "warning",
          confirmButtonText: `Confirmar`,
          denyButtonText: `Cancelar`,
          confirmButtonColor: "#28a745",
        }).then((result) => {
          if (result.isConfirmed) {
            Swal.fire({
              icon: "success",
              title: "Pedido Alterado",
              showConfirmButton: false,
              timer: 1500,
            });

            that.pedidos = this.pedidos.map((p) => {
              return p.id === pedido.id ? pedido : p;
            });
          }
          if (result.isDenied) {
            Swal.fire({
              icon: "info",
              title: "Alterações desfeitas",
              showConfirmButton: false,
              timer: 1500,
            });
          }
        });
      }
    },

    aprovarPedido(pedido) {
      const that = this;
      const alteracoes = pedido.produtos
        .filter((p) => p.alterado)
        .map((p) => {
          return {
            id: p.id,
            quantidade: parseInt(p.quantidade),
          };
        });

      Swal.fire({
        title: "Confirmação:<br>Aprovar Pedido?",
        showDenyButton: true,
        icon: "warning",
        confirmButtonText: `Confirmar`,
        denyButtonText: `Cancelar`,
        confirmButtonColor: "#28a745",
      }).then((result) => {
        if (result.isConfirmed) {
          loja
            .salvarResultadoAvaliacao(pedido.id, {
              alteracoes: alteracoes,
              operacao: "APROVAR_PEDIDO",
            })
            .then((res) => {
              console.log(res.data);
              Swal.fire({
                icon: "success",
                title: "Pedido Aprovado",
                showConfirmButton: false,
                timer: 1500,
              });

              that.pedidos = that.pedidos.filter((p) => p !== pedido) || [];
              console.log(that.pedidos.length);
              if(!that.pedidos.length) 
              {
                this.info = "Não há pedidos para serem Avaliados";
              }
            });
        } else if (result.isDenied) {
          Swal.fire({
            icon: "info",
            title: "O Pedido continuará Pendente para Aprovação",
            showConfirmButton: false,
            timer: 1500,
          });
        }
      });
    },

    recusarPedido(pedido) {
      const that = this;

      Swal.fire({
        title: "Confirmação:<br>Cancelar Pedido?",
        showDenyButton: true,
        icon: "warning",
        confirmButtonText: `Confirmar`,
        denyButtonText: `Cancelar`,
        confirmButtonColor: "#28a745",
      }).then((result) => {
        if (result.isConfirmed) {
          loja
            .salvarResultadoAvaliacao(pedido.id, {
              alteracoes: [],
              operacao: "CANCELAR_PEDIDO",
            })
            .then((res) => {
              console.log(res.data);
              Swal.fire({
                icon: "success",
                title: "Pedido Cancelado",
                showConfirmButton: false,
                timer: 1500,
              });

            that.pedidos = that.pedidos.filter((p) => p !== pedido) || [];
            console.log(that.pedidos.length);
            if(!that.pedidos.length) 
              {
                this.info = "Não há pedidos para serem Avaliados";
              }
            });
        } else if (result.isDenied) {
          Swal.fire({
            icon: "info",
            title: "O Pedido continuará Pendente para Aprovação",
            showConfirmButton: false,
            timer: 1500,
          });
        }
      });
    },
  },
};
</script>