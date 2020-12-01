import { http } from "./config";

export default {
  listar: () => {
    return http.get("api/precomunidade");
  },

  buscarPorId: (id) => {
    return http.get("api/precomunidade/" + id);
  },

  inserir: (precomunidade) => {
    return http.post("api/precomunidade", precomunidade);
  },

  alterar: (precomunidade) => {
    return http.put("api/precomunidade", precomunidade);
  },

  delete: (id) => {
    return http.delete("api/precomunidade/" + id);
  },
};
