<template>
  <h3>로그인</h3>
  <form @submit.prevent="login">
    <div>
      <input type="text" v-model="accountId" placeholder="아이디">
    </div>
    <div>
      <input type="password" v-model="password" placeholder="비밀번호">
    </div>
    <div>
      <button> 로그인</button>
    </div>
  </form>
  <button @click="moveToSignUpPage"> 회원가입</button>
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

      await this.$store.dispatch('login', actionPayload);

      this.$router.push('/home');
    }
  }
}
</script>

<style scoped>
div {
  margin: 0.5rem 0;
}
</style>
