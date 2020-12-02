import { http } from "./config";

export default {
    getCheckout: (cpf) => {
        return http.get("/api/loja/pedidos", {
            headers: {
                cpf: cpf
            }
        });
    },

    postCheckout: (cpf, body) => {
        return http.post("/api/loja/pedidos", body, {
            headers: {
                cpf: cpf
            }
        });
    },


    getProdutosDisponiveisVendaByCategoria: (cpf, pagina) => {
        return http.get("/api/loja/produtos", {
            headers: {
                cpf: cpf
            },
            params: {
                pagina: pagina
            }
        });
    },

    listarCarrinhos: (cpf) => {
        return http.get("/api/loja/carrinhos", {
            headers: {
                cpf: cpf
            }
        });
    },

    sincronizarProduto: (cpf, estoqueProdutorId, quantidade) => {
        return http.post("/api/loja/carrinhos", {
            cotacaoId: estoqueProdutorId,
            quantidade: quantidade
        }, { headers: { cpf: cpf } });
    },

    consultarPedidos: (status) => {
        return http.get("/api/loja/pedidos/salvos", {
            params: {
                status
            }
        });
    },

    salvarResultadoAvaliacao: (idPedido, body) => {
        return http.put("/api/loja/pedidos/" + idPedido + "/", body);
    }

};