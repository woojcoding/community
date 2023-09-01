<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul id="nav1" class="nav justify-content-start bg-light">
            <li class="nav-item">
              <router-link class="nav-link" to="/boards/notice">공지사항</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/boards/free">자유 게시판</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/boards/gallery">갤러리</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/boards/help">문의 게시판</router-link>
            </li>
          </ul>
        </div>
      </div>
      <div class="navbar-right">
        <div v-if="isLoggedIn" >
          {{ name }} 님 환영합니다
          <button @click="logout" class="btn btn-danger">Logout</button>
        </div>
        <div v-else>
          <router-link class="btn btn-primary" to="/login">로그인 / 회원가입</router-link>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "TheNavigator",
  data() {
    return {
      name: ""
    }
  },
  created() {
    this.name = this.$store.getters.name;
  },
  watch: {
    isLoggedIn(newValue) {
      if (newValue) {
        this.name = this.$store.getters.name;
      }
    },
  },
  computed: {
    isLoggedIn() {
      return this.$store.getters.isAuthenticated;
    },
  },
  methods: {
    /**
     * 로그아웃 하는 메서드
     */
    logout() {
      this.$store.dispatch('logout');
    }
  }
}
</script>

<style scoped>

</style>
