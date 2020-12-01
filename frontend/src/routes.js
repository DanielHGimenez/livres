import Home from "./components/Home";
import ListaConsumidores from "./components/ListaConsumidores";
import ListaPreComunidades from "./components/ListaPreComunidades";
import CadConsumidores from "./components/CadConsumidores";
import CadPreComunidade from "./components/CadPreComunidade";
import Produtos from "./components/LstProdutos";
import Checkout from "./components/Checkout";

export default [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/consumidores",
    name: "ListaConsumidores",
    component: ListaConsumidores,
  },
  {
    path: "/consumidores/cadastro",
    name: "CadConsumidores",
    props: true,
    component: CadConsumidores,
  },
  {
    path: "/precomunidades",
    name: "ListaPreComunidades",
    component: ListaPreComunidades,
  },
  {
    path: "/precomunidades/cadastro",
    name: "CadPreComunidade",
    props: true,
    component: CadPreComunidade,
  },
  {
    path: "/produtos",
    name: "Produtos",
    component: Produtos,
  },
  {
    path: "/carrinho",
    name: "Checkout",
    component: Checkout,
  },
];
