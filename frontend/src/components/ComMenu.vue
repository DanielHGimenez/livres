<template>
  <nav class="main-menu">
    <ul>
      <li v-for="item in items" :key="item.id">
        <router-link v-bind:to="item.href">
          <i v-bind:class="item.icon"></i>
          <span class="nav-text">{{ item.name }}</span>
        </router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  data: function() {
    return {
      items: [
        {
          icon: "fa fa-home fa-2x",
          name: "Home",
          href: "/"
        },
        {
          icon: "fa fa-users fa-2x",
          name: "Pré-comunidade",
          href: "/precomunidades"
        },
        {
          icon: "fa fa-user fa-2x",
          name: "Consumidores",
          href: "/consumidores"
        },
        {
          icon: "fa fa-shopping-basket fa-2x",
          name: "Produtos",
          href: "/produtos"
        },
        {
          icon: "fa fa-shopping-cart fa-2x",
          name: "Carinho",
          href: "/carinho"
        },
        {
          icon: "fa fa-check-square fa-2x",
          name: "Aprovação de Pedidos",
          href: "/aprovacaoPedidos"
        }
      ]
    };
  }
};
</script>
