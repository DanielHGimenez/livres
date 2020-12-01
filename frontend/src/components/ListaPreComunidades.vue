<template>
  <div>
    <h2>Pré-comunidades</h2>

    <table class="container table">
      <thead class="thead">
        <tr>
          <th scope="col">Name</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="precomunidade in precomunidades" :key="precomunidade.id">
          <td>{{ precomunidade.nome }}</td>
          <td class="row justify-content-end">
            <router-link class="btn btn-outline-primary mr-3" to="precomunidades/cadastro">
              <i class="fas fa-pencil-alt"></i>
            </router-link>
            <a
              class="btn btn-outline-danger"
              type="button"
              data-toggle="modal"
              data-target="#deletarModal"
              @click="IdentificarId(precomunidade.id)"
            >
              <i class="fas fa-trash-alt"></i>
            </a>
          </td>
        </tr>
      </tbody>
    </table>
    <router-link class="btn btn-outline-primary mr-3" to="precomunidades/cadastro">
      <i class="fas fa-plus"></i>
    </router-link>

    <com-modal-delete
      mensagem="Deseja excluir esta pré-comunidade?"
      v-on:confirmar="ExcluirPrecomunidade"
    />
  </div>
</template>

<script>
import PreComunidades from "../services/precomunidade";
import modalDelete from "./modalDelete";

export default {
  components: {
    "com-modal-delete": modalDelete
  },
  data() {
    return {
      precomunidades: [],
      idParaExclusao: 0
    };
  },
  methods: {
    ListarPreComunidades: function() {
      PreComunidades.listar()
        .then(result => {
          this.precomunidades = result.data;
        })
        .catch(() => {
          this.$toaster.error("Erro ao carregar lista de Pré-comunidades");
        });
    },
    ExcluirPrecomunidade: function() {
      PreComunidades.delete(this.idParaExclusao)
        .then(() => {
          this.$toaster.success("Pré-comunidade removida com sucesso");
          this.ListarPreComunidades();
        })
        .catch(() => {
          this.$toaster.error("Não foi possível remover a pré-comunidade");
        });
    },
    IdentificarId: function(_id) {
      this.idParaExclusao = _id;
    }
  },
  mounted() {
    this.ListarPreComunidades();
  }
};
</script>