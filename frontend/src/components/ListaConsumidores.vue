<template>
  <div>
    <h2>Consumidores</h2>

    <table class="container table">
      <thead class="thead">
        <tr>
          <th scope="col">Nome</th>
          <th scope="col">Sobrenome</th>
          <th scope="col">CPF</th>
          <th scope="col">Pré-comunidade</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="consumidor in consumidores" :key="consumidor.cpf">
          <td>{{ consumidor.nome }}</td>
          <td>{{ consumidor.sobrenome }}</td>
          <td>{{ consumidor.cpf }}</td>
          <td>{{ consumidor.precomunidade }} - {{ consumidor.nomeprecomunidade }}</td>
          <td class="row justify-content-end">
            <router-link
              class="btn btn-outline-primary mr-3"
              :to="{name: 'CadConsumidores', params:{consumidorAlterar: consumidor}}"
            >
              <i class="fas fa-pencil-alt"></i>
            </router-link>
            <a
              class="btn btn-outline-danger"
              type="button"
              data-toggle="modal"
              data-target="#deletarModal"
              @click="IdentificarCpf(consumidor.cpf)"
            >
              <i class="fas fa-trash-alt"></i>
            </a>
          </td>
        </tr>
      </tbody>
    </table>
    <router-link class="btn btn-outline-primary mr-3" to="consumidores/cadastro">
      <i class="fas fa-plus"></i>
    </router-link>

    <com-modal-delete
      mensagem="Deseja excluir este consumidor?"
      v-on:confirmar="ExcluirConsumidor"
    />
  </div>
</template>

<script>
import Consumidores from "../services/consumidores";
import PreComunidades from "../services/precomunidade";
import modalDelete from "./modalDelete";

export default {
  components: {
    "com-modal-delete": modalDelete
  },
  data() {
    return {
      consumidores: [],
      cpfParaExclusao: ""
    };
  },
  methods: {
    ListarConsumidores: function() {
      Consumidores.listar()
        .then(result => {
          result.data.forEach(data => {
            PreComunidades.buscarPorId(data.precomunidade).then(result => {
              this.consumidores.push({
                nome: data.nome,
                sobrenome: data.sobrenome,
                cpf: data.cpf,
                senha: data.senha,
                precomunidade: data.precomunidade,
                nomeprecomunidade: result.data.nome
              });
            });
          });
        })
        .catch(() => {
          this.$toaster.error("Erro ao carregar lista de consumidores");
        });
    },
    ExcluirConsumidor: function() {
      Consumidores.delete(this.cpfParaExclusao)
        .then(() => {
          this.$toaster.success("Consumidor removido com sucesso");
          this.ListarConsumidores();
        })
        .catch(() => {
          this.$toaster.error("Não foi possível remover o consumidor");
        });
    },
    IdentificarCpf: function(_cpf) {
      this.cpfParaExclusao = _cpf;
    }
  },
  mounted() {
    this.ListarConsumidores();
  }
};
</script>
