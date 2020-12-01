<template>
  <div class="row">
    <div class="col-md-6 col-sm-12 offset-md-3">
      <div class="card">
        <div class="card-header">Cadastro de Pre Comunidade</div>
        <div class="card-body">
          <form>
            <div class="md-form">
              <input
                autocomplete="off"
                type="text"
                id="nome"
                class="form-control"
                v-model.trim="precomunidade.nome"
              />
              <label :class="{active: preenchido}" for="nome">Nome</label>
            </div>
            <div class="text-right">
              <router-link to="/precomunidades" type="button" class="btn">Cancelar</router-link>
              <button type="button" class="btn btn-primary" @click="cadastrar()">
                <i class="fa fa-floppy-o"></i> Cadastrar
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PreComunidade from "../services/precomunidade";

export default {
  props: {
    precomunidadeAlterar: {
      type: Object
    }
  },
  data() {
    return {
      preenchido: false,
      precomunidade: { id: 0, nome: "" }
    };
  },
  methods: {
    cadastrar: function() {
      if (this.precomunidade.id != 0) {
        this.AlterarPreComunidade();
      } else {
        this.AdicionarPreComunidade();
      }
    },
    AdicionarPreComunidade: function() {
      PreComunidade.inserir(this.precomunidade)
        .then(() => {
          this.$toaster.success("Pré-comunidade adicionada com sucesso");
          this.$router.push("/precomunidades");
        })
        .catch(() => {
          this.$toaster.error("Não foi possível adicionar pré-comunidade");
        });
    },
    AlterarPreComunidade: function() {
      PreComunidade.alterar(this.precomunidade)
        .then(() => {
          this.$toaster.success("Pré-comunidade alterada com sucesso");
          this.$router.push("/precomunidades");
        })
        .catch(() => {
          this.$toaster.success("Não foi possível alterar pré-comunidade");
        });
    }
  },
  created() {
    if (this.precomunidadeAlterar != null) {
      this.preenchido = true;
      this.precomunidade = this.precomunidadeAlterar;
    }
  }
};
</script>

<style>
</style>
