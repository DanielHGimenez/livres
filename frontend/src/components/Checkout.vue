<template>
  <div class="row">
    <div class="col-md-8 offset-md-2" v-show="step === 1">
      <div class="card">
        <div class="card-header">Carrinho</div>
        <div class="card-body">
          <div v-show="!produtos.length">
             <h5 class="text-center">{{ info }}</h5>
          </div>
          <div v-show="produtos.length">
            <div class="table-responsive">
              <table
                id="dtBasicExample"
                class="table table-bordered table-sm"
                cellspacing="0"
                width="100%"
              >
                <thead class="thead-dark">
                  <tr>
                    <th class="th-sm">Nome</th>
                    <th class="th-sm text-right">Quantidade</th>
                    <th class="th-sm text-right">Preço</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="produto in produtos" :key="produto.id">
                    <td>{{ produto.nome }}</td>
                    <td class="text-right">{{ produto.quantidade }}</td>
                    <td class="text-right">{{ produto.preco }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="text-right">
              <h4>Valor total: {{ valorTotal }}</h4>
            </div>
            <div class="text-right">
              <button
                type="button"
                class="btn btn-primary"
                @click="next()"
                :disabled="!produtos.length"
              >
                Continuar
                <i class="fa fa-share"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-8 offset-md-2" v-show="step === 2">
      <div class="card">
        <div class="card-header">Dados de Entrega</div>
        <div class="card-body">
          <form>
            <div class="md-form">
              <input
                autocomplete="off"
                type="email"
                id="nome"
                class="form-control"
                v-model.trim="nome"
              />
              <label for="nome">Nome</label>
            </div>
            <div class="row">
              <div class="col-md-2">
                <div class="md-form">
                  <input
                    autocomplete="off"
                    type="text"
                    id="cep"
                    class="form-control"
                    v-model.trim="cep"
                    @blur="buscaPorCep()"
                  />
                  <label for="cep">CEP</label>
                </div>
              </div>
              <div class="col-md-2">
                <div class="md-form">
                  <input
                    autocomplete="off"
                    type="text"
                    id="estado"
                    class="form-control"
                    v-model.trim="estado"
                  />
                  <label for="estado" :class="{ active: estado }">Estado</label>
                </div>
              </div>
              <div class="col-md-4">
                <div class="md-form">
                  <input
                    autocomplete="off"
                    type="text"
                    id="cidade"
                    class="form-control"
                    v-model.trim="cidade"
                  />
                  <label for="cidade" :class="{ active: cidade }">Cidade</label>
                </div>
              </div>
              <div class="col-md-4">
                <div class="md-form">
                  <input
                    autocomplete="off"
                    type="text"
                    id="bairro"
                    class="form-control"
                    v-model.trim="bairro"
                  />
                  <label for="bairro" :class="{ active: bairro }">Bairro</label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-6">
                <div class="md-form">
                  <input
                    autocomplete="off"
                    type="text"
                    id="endereco"
                    class="form-control"
                    v-model.trim="endereco"
                  />
                  <label for="endereco" :class="{ active: endereco }"
                    >Endereço</label
                  >
                </div>
              </div>
              <div class="col-md-2">
                <div class="md-form">
                  <input
                    autocomplete="off"
                    type="text"
                    id="numero"
                    class="form-control"
                    v-model.trim="numero"
                  />
                  <label for="numero">Número</label>
                </div>
              </div>
              <div class="col-md-4">
                <div class="md-form">
                  <input
                    autocomplete="off"
                    type="text"
                    id="complemento"
                    class="form-control"
                    v-model.trim="complemento"
                  />
                  <label for="complemento">Complemento</label>
                </div>
              </div>
            </div>
            <div class="text-right">
              <button type="button" class="btn btn-primary" @click="prev()">
                Voltar
                <i class="fa fa-reply"></i>
              </button>
              <button type="button" class="btn btn-primary" @click="next()">
                Continuar
                <i class="fa fa-share"></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="col-md-8 offset-md-2" v-show="step === 3">
      <div class="card">
        <div class="card-header">Método de Pagamento</div>
        <div class="card-body">
          <form>
            <div class="pt-3"></div>
            <div class="select">
              <select class="select-text" required v-model="metodoPagamento">
                <option disabled selected></option>
                <option
                  v-for="option in metodosPagamento"
                  v-bind:value="option.nome"
                  :key="option.nome"
                >
                  {{ option.nome }}
                </option>
              </select>
              <span class="select-highlight"></span>
              <span class="select-bar"></span>
              <label class="select-label">Metodo de Pagamento:</label>
            </div>
            <div class="pt-3"></div>
            <div class="pt-3"></div>
            <div class="select" v-show="metodoPagamento">
              <select class="select-text" required v-model="meioPagamento">
                <option disabled selected></option>
                <option
                  v-for="option in getMeiosPagamento()"
                  v-bind:value="option"
                  :key="option"
                >
                  {{ option }}
                </option>
                >
              </select>
              <span class="select-highlight"></span>
              <span class="select-bar"></span>
              <label class="select-label">Forma de Pagamento:</label>
            </div>
            <div class="pt-3"></div>
            <div class="text-right">
              <button type="button" class="btn btn-primary" @click="prev()">
                Voltar
                <i class="fa fa-reply"></i>
              </button>
              <button
                type="button"
                class="btn btn-primary"
                @click="next()"
                :disabled="!meioPagamento"
              >
                Continuar
                <i class="fa fa-share"></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="col-md-8 offset-md-2" v-show="step === 4">
      <div class="card">
        <div class="card-header">Resumo do Pedido</div>
        <div class="card-body">
          <div class="card">
            <div class="card-header">Dados de Entrega</div>
            <div class="card-body">
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Nome:</span>
                  {{ nome }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">CEP:</span>
                  {{ cep }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">UF:</span>
                  {{ estado }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Cidade:</span>
                  {{ cidade }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Bairro:</span>
                  {{ bairro }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Endereço:</span>
                  {{ endereco }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Nº:</span>
                  {{ numero }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Complemento:</span>
                  {{ complemento }}
                </label>
              </div>
            </div>
          </div>
          <div class="pt-3"></div>
          <div class="card">
            <div class="card-header">Forma de Pagamento</div>
            <div class="card-body">
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Modalidade:</span>
                  {{ metodoPagamento }}
                </label>
              </div>
              <div class="row">
                <label class="col">
                  <span class="font-weight-bold">Método:</span>
                  {{ meioPagamento }}
                </label>
              </div>
            </div>
          </div>
          <div class="pt-3"></div>
          <div class="card">
            <div class="card-header">Itens do Pedido</div>
            <div class="card-body">
              <div class="table-responsive">
                <table
                  id="dtBasicExample"
                  class="table table-bordered table-sm"
                  cellspacing="0"
                  width="100%"
                >
                  <thead class>
                    <tr>
                      <th class="th-sm">Nome</th>
                      <th class="th-sm">Quantidade</th>
                      <th class="th-sm text-right">Preço</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="produto in produtos" :key="produto.id">
                      <td>{{ produto.nome }}</td>
                      <td class="text-right">{{ produto.quantidade }}</td>
                      <td class="text-right">{{ produto.preco }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="text-right">
                <h4>Valor total: {{ valorTotal }}</h4>
              </div>
            </div>
          </div>
          <div class="pt-2"></div>
          <div class="text-right">
            <button
              type="button"
              class="btn btn-success"
              @click="finalizarPedido()"
            >
              <i class="fa fa-shopping-cart"></i> Finalizar Pedido
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import loja from "@/services/loja.js";
import viacep from "@/services/viacep.js";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      step: 1, //1
      info: "Carregando...",
      produtor: "",
      valorTotal: "",
      nome: "",
      cep: "",
      estado: "",
      cidade: "",
      bairro: "",
      endereco: "",
      numero: "",
      complemento: "",
      metodoPagamento: "",
      meioPagamento: "",
      produtos: [],
      metodosPagamento: [],
    };
  },
  created() {
    const that = this;
    loja.getCheckout(191).then((response) => {
      that.produtos = response.data.produtos || [];
      if(!that.produtos.length){
        that.info = "Carrinho vazio!";
      }

      if (that.produtos.length) {
        that.produtos.map(
          (p) =>
            (p.preco =
              "R$ " +
              p.preco.toLocaleString("pt-BR", {
                maximumFractionDigits: 2,
                minimumFractionDigits: 2,
              }))
        );

        that.valorTotal =
          "R$ " +
          response.data.valorTotal.toLocaleString("pt-BR", {
            maximumFractionDigits: 2,
            minimumFractionDigits: 2,
          });

        that.metodosPagamento = response.data.metodosPagamento;
      }
    });
  },
  methods: {
    buscaPorCep() {
      const that = this;

      viacep
        .buscaPorCep(this.cep)
        .then((response) => {
          const data = response.data;
          that.estado = data.uf;
          that.cidade = data.localidade;
          that.bairro = data.bairro;
          that.endereco = data.logradouro;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    prev() {
      this.step--;
    },
    next() {
      this.step++;
    },
    getMeiosPagamento() {
      const metodo = this.metodosPagamento.find(
        (m) => m.nome === this.metodoPagamento
      );
      return metodo ? metodo.meiosPagamento : [];
    },
    finalizarPedido() {
      const that = this;

      loja
        .postCheckout(191, {
          bairro: this.bairro,
          cep: parseInt(this.cep),
          cidade: this.cidade,
          complemento: this.complemento,
          destinatario: this.nome,
          endereco: this.endereco,
          estado: this.estado,
          meioPagamento: this.meioPagamento,
          metodoPagamento: this.metodoPagamento,
          numero: parseInt(this.numero),
        })
        .then(() => {
          Swal.fire({
            icon: "success",
            title: "Status",
            text: "Pedido enviado para a análise!",
          }).then(() => {
            that.$router.push("/");
          });
        });
    },
  },
};
</script>
