<template>
  <div>
    <nav>
      <router-link to="/boards/notice"> 공지사항</router-link>
      |
      <router-link to="/boards/free"> 자유 게시판</router-link>
      |
      <router-link to="/boards/gallery"> 갤러리</router-link>
      |
      <router-link to="/boards/help"> 문의 게시판</router-link>
    </nav>
    <div v-if="isLoggedIn">
      <div> {{name}} 님 환영합니다</div>
      <button @click="logout">Logout</button>
    </div>
    <div v-else>
      <router-link to="/login"> 로그인 / 회원가입</router-link>
    </div>
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
  computed: {
    isLoggedIn() {
      return this.$store.getters.isAuthenticated;
    },
  },
  created() {
    if (this.isLoggedIn) {
      this.name = this.$store.getters.name;
    }
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
