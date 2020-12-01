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
              <label for="nome">Nome</label>
            </div>
            <div class="text-right">
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
  data() {
    return {
      precomunidade: { nome: "" }
    };
  },
  methods: {
    cadastrar: function() {
      // if cpf existe
      // alterar
      // else
      this.SalvarPreComunidade();
    },
    SalvarPreComunidade: function() {
      PreComunidade.inserir(this.precomunidade)
        .then(() => {
          this.$toaster.success("Pré-comunidade adicionada com sucesso");
          this.$router.push("/precomunidades");
        })
        .catch(() => {
          this.$toaster.error("Não foi possível adicionar pré-comunidade");
        });
    }
  }
};
</script>

<style></style>
