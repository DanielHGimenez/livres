import { http } from "./config";

export default {
  listar: () => {
    return http.get("api/consumidor");
  },

  inserir: (consumidor) => {
    return http.post("api/consumidor/", consumidor);
  },

  buscarPorCpf: (cpf) => {
    return http.get("api/consumidor/" + cpf);
  },

  delete: (cpf) => {
    return http.delete("api/consumidor/" + cpf);
  },
};
