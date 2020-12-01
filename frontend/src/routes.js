import Home from "./components/Home";
import ListaConsumidores from "./components/ListaConsumidores";
import ListaPreComunidades from "./components/ListaPreComunidades";
import CadConsumidores from "./components/CadConsumidores";
import CadPreComunidade from "./components/CadPreComunidade";
import Produtos from "./components/LstProdutos";
import Checkout from "./components/Checkout";
import AprovacaoPedidos from "./components/AprovacaoPedidos";

export default [
    { path: "/", component: Home },
    { path: "/consumidores", component: ListaConsumidores },
    { path: "/consumidores/cadastro", component: CadConsumidores },
    { path: "/precomunidades", component: ListaPreComunidades },
    { path: "/precomunidades/cadastro", component: CadPreComunidade },
    { path: "/produtos", component: Produtos },
    { path: "/carinho", component: Checkout },
    { path: "/aprovacaoPedidos", component: AprovacaoPedidos }
];