import { http } from "./config";

export default {
  listar: () => {
    return http.get("api/precomunidade");
  },

  inserir: (precomunidade) => {
    return http.post("api/precomunidade", precomunidade);
  },

  delete: (id) => {
    return http.delete("api/precomunidade/" + id);
  },
};
