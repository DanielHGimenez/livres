<template>
  <nav class="main-navbar navbar fixed-top navbar-expand-lg navbar-light bg-white">
    <router-link class="navbar-brand" to="/">
      <img
        src="https://i.pinimg.com/originals/c7/65/3f/c7653f9e8d1d0e13212970213ebc76f6.png"
        width="30"
        height="30"
        class="d-inline-block align-top"
        alt
        loading="lazy"
      />
      Livres
    </router-link>

    <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav mr-auto mt-2 mt-lg-0"></ul>
      <ul class="navbar-nav my-2 my-lg-0">
        <li class="nav-item">
          <router-link
            to="/carrinho"
            class="nav-link"
            id="navbarDropdownMenuLink"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            <i class="fa fa-shopping-cart badge-notification" data-badge="9" aria-hidden="true"></i>
          </router-link>
        </li>
        <li class="nav-item dropdown">
          <a
            class="nav-link"
            href="#"
            id="navbarDropdownMenuLink"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            <i class="fa fa-user" aria-hidden="true"></i>
          </a>
        </li>
      </ul>
    </div>
  </nav>
</template>
