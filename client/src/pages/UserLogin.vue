<template>
  <div class="login-container">
    <div class="container mt-4">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card">
            <div class="card-body">
              <h3 class="card-title">로그인</h3>
              <form @submit.prevent="login">
                <div class="mb-3">
                  <input type="text" class="form-control" v-model="accountId"
                         id="accountId" placeholder="아이디">
                </div>
                <div class="mb-3">
                  <input type="password" class="form-control" v-model="password"
                         id="password" placeholder="비밀번호">
                </div>
                <div class="mb-3">
                  <button type="submit" class="btn btn-primary">로그인</button>
                </div>
              </form>
              <button @click="moveToSignUpPage" class="btn btn-primary">회원가입
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "user-login",
  data() {
    return {
      accountId: "",
      password: "",
    };
  },
  methods: {
    moveToSignUpPage() {
      this.$router.push('/signUp');
    },
    async login() {
      const actionPayload = {
        accountId: this.accountId,
        password: this.password,
      };

      try {
        await this.$store.dispatch('login', actionPayload);
        this.$router.push('/');
      } catch (error) {
        alert(error);
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
