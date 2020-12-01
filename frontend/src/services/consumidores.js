import { http } from "./config";

export default {
  listar: () => {
    return http.get("api/consumidor");
  },

  buscarPorCpf: (cpf) => {
    return http.get("api/consumidor/" + cpf);
  },

  inserir: (consumidor) => {
    return http.post("api/consumidor", consumidor);
  },

  alterar: (consumidor) => {
    return http.put("api/consumidor", consumidor);
  },

  delete: (cpf) => {
    return http.delete("api/consumidor/" + cpf);
  },
};
