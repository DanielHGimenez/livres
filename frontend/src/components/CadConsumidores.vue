<template>
  <div class="row">
    <div class="col-md-6 col-sm-12 offset-md-3">
      <div class="card">
        <div class="card-header">Cadastro de Consumidores</div>
        <div class="card-body">
          <form id="formCadConsumidores">
            <div class="md-form">
              <input
                autocomplete="off"
                type="text"
                id="nome"
                class="form-control"
                v-model.trim="consumidor.nome"
              />
              <label for="nome">Nome</label>
            </div>

            <div class="md-form">
              <input
                autocomplete="off"
                type="text"
                id="sobrenome"
                class="form-control"
                v-model.trim="consumidor.sobrenome"
              />
              <label for="sobrenome">Sobrenome</label>
            </div>

            <div class="md-form">
              <input
                autocomplete="off"
                type="text"
                id="cpf"
                class="form-control"
                v-model.trim="consumidor.cpf"
              />
              <label for="cpf">CPF</label>
            </div>

            <div class="md-form">
              <input
                autocomplete="off"
                type="password"
                id="senha"
                class="form-control"
                v-model.trim="consumidor.senha"
              />
              <label for="senha">Senha</label>
            </div>

            <div class="pt-1"></div>

            <div class="select">
              <select class="select-text" required v-model="consumidor.precomunidade">
                <option disabled selected></option>
                <option
                  v-for="option in precomunidades"
                  :key="option.id"
                  v-bind:value="option.id"
                >{{ option.nome }}</option>>
              </select>
              <span class="select-highlight"></span>
              <span class="select-bar"></span>
              <label class="select-label">Pré-comunidade:</label>
            </div>

            <div class="pt-3"></div>

            <div class="row">
              <div class="col text-right">
                <button type="button" class="btn btn-primary pull-right" @click="cadastrar()">
                  <i class="fa fa-floppy-o"></i> Cadastrar
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Consumidores from "../services/consumidores";
import PreComunidades from "../services/precomunidade";

export default {
  data() {
    return {
      consumidor: {
        nome: "",
        sobrenome: "",
        cpf: "",
        senha: "",
        precomunidade: 0
      },
      precomunidades: []
    };
  },
  methods: {
    cadastrar: function() {
      // if cpf existe
      // alterar
      // else
      this.SalvarConsumidor();
    },
    SalvarConsumidor: function() {
      Consumidores.inserir(this.consumidor)
        .then(() => {
          this.$toaster.success("Consumidor adicionado com sucesso");
          this.$router.push("/consumidores");
        })
        .catch(() => {
          this.$toaster.error("Não foi possível adicionar consumidor");
        });
    }
  },
  mounted() {
    PreComunidades.listar()
      .then(result => {
        this.precomunidades = result.data;
      })
      .catch(() => {
        this.$toaster.error("Erro ao carregar lista pré-comunidades");
      });
  }
};
</script>

<style></style>
