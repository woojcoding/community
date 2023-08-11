<template>
  <h3>로그인</h3>
  <form @submit.prevent="">
    <div>
      <input type="text" placeholder="아이디">
    </div>
    <div>
      <input type="password" placeholder="비밀번호">
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
      accountId: {
        type: String,
        required: true
      },
      password: {
        type: String,
        required: true
      }
    };
  },
  methods: {
    moveToSignUpPage() {
      this.$router.push('/signUp');
    }
  }
}
</script>

<style scoped>
  div {
    margin: 0.5rem 0;
  }
</style>
