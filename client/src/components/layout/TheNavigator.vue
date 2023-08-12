<template>
  <div>
    <nav>
      <router-link to="/boards"> 공지사항</router-link>
      |
      <router-link to="/boards"> 자유 게시판</router-link>
      |
      <router-link to="/boards"> 갤러리</router-link>
      |
      <router-link to="/boards"> 문의 게시판</router-link>
    </nav>
    <div v-if="isLoggedIn">
      <div> {{accountId}} 님 환영합니다</div>
      <button @click="logout">Logout</button>
    </div>
    <div v-else>
      <router-link to="/login"> 로그인</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "TheNavigator",
  data() {
    return {
      accountId: ""
    }
  },
  computed: {
    isLoggedIn() {
      return this.$store.getters.isAuthenticated;
    },
  },
  created() {
    if (this.isLoggedIn) {
      this.accountId = this.$store.getters.accountId;
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout');
    }
  }
}
</script>

<style scoped>

</style>
