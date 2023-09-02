<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light p-4">
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul id="nav1" class="nav bg-light">
          <li class="nav-item">
            <router-link class="nav-link custom-link" to="/boards/notice">공지사항</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link custom-link" to="/boards/free">자유 게시판</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link custom-link" to="/boards/gallery">갤러리</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link custom-link" to="/boards/help">문의 게시판</router-link>
          </li>
        </ul>
      </div>
      <div class="navbar-right">
        <div v-if="isLoggedIn">
          <span class="user-greeting">{{ name }} 님 환영합니다</span>
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

      this.$router.push("/")
    }
  }
}
</script>

<style scoped>
.custom-link {
  color: #007bff; 
  font-weight: bold;
  text-decoration: none;
  transition: color 0.2s ease-in-out;
}

.custom-link:hover {
  color: #0056b3;
}

.user-greeting {
  margin-right: 10px;
}
</style>
