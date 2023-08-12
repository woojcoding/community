<template>
  <h3>회원가입</h3>
  <form @submit.prevent="signUp">
    <div>
      <input type="text" v-model="userDto.accountId" placeholder="아이디">
      <button type="button" @click="confirmIdDuplication">중복확인</button>
    </div>
    <div>
      <input type="password" v-model="userDto.password" placeholder="비밀번호">
    </div>
    <div>
      <input type="password" v-model="userDto.passwordConfirm" placeholder="비밀번호 확인">
    </div>
    <div>
      <input type="text" v-model="userDto.name" placeholder="이름">
    </div>
    <div>
      <button>회원 가입 하기</button>
    </div>
  </form>
</template>

<script>
import {confirmIdDuplication, signUpUser} from "@/api/userService";

export default {
  name: "UserSignUp",
  data() {
    return {
      userDto: {
        accountId: "",
        password: "",
        passwordConfirm: "",
        name: ""
      },
      isAccountIdDuplicated: true,
      passwordError: false,
      passwordConfirmError: false,
      nameError: false,
      errorMessages: []
    };
  },
  watch: {
    'userDto.accountId': function(newValue, oldValue) {
      if (newValue !== oldValue) {
        this.isAccountIdDuplicated = true;
      }
    }
  },
  methods: {
    /**
     * 회원가입을 하는 메서드
     *
     * @returns {Promise<void>}
     */
    async signUp() {
      if (this.isAccountIdDuplicated) {
        alert("아이디 중복체크를 통과해주세요.");
      } else {
        this.validateForm();

        if (this.errorMessages.length === 0) {
          const isSuccess = await signUpUser(this.userDto);

          if (isSuccess) {
            this.$router.push("/login"); // 성공 시에만 라우터 이동
          }
        } else {
          alert(this.errorMessages.join("\n"));
          this.errorMessages.length = 0;
        }
      }
    },
    /**
     * 아이디 중복체크를 하는 메서드
     *
     * @returns {Promise<void>}
     */
    async confirmIdDuplication() {
      const isDuplicated = await confirmIdDuplication(this.userDto.accountId);
      this.handleIsAccountIdDuplicated(isDuplicated);
    },
    /**
     * IsAccountId의 중복 여부를 바꾸는 메서드
     *
     * @param isDuplicated 중복 여부
     */
    handleIsAccountIdDuplicated(isDuplicated) {
      this.isAccountIdDuplicated = isDuplicated;
    },
    /**
     * 폼을 검증하는 메서드
     */
    validateForm() {
      this.validatePassword();
      this.validatePasswordConfirm();
      this.validateName();
    },
    /**
     * 패스워드의 유효성 검증을 하는 메서드
     */
    validatePassword() {
      const passwordRegex = /^[a-zA-Z0-9!@#$%^&*()-_+=]{4,11}$/;

      if (!passwordRegex.test(this.userDto.password)) {
        this.errorMessages.push("비밀번호는 영문 대/소문자, 숫자, 특수문자로 4~11자여야 합니다.");
      } else if (this.userDto.password.length >= 4 && this.userDto.password.length < 12) {
        const consecutiveRegex = /(.)\1\1/;

        if (consecutiveRegex.test(this.userDto.password)) {
          this.errorMessages.push("비밀번호에 연속된 문자 3번 이상 사용할 수 없습니다.");
        } else if (this.userDto.password === this.userDto.accountId) {
          this.errorMessages.push("비밀번호는 아이디와 동일할 수 없습니다.");
        }
      }
    },
    /**
     * 패스워드 확인과 패스워드가 일치하는지 검증하는 메서드
     */
    validatePasswordConfirm() {
      this.passwordConfirmError = this.userDto.password !== this.userDto.passwordConfirm;

      if (this.passwordConfirmError) {
        this.errorMessages.push("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      }
    },
    /**
     * 이름의 유효성 검증을 하는 메서드
     */
    validateName() {
      this.nameError = this.userDto.name.length < 2 || this.userDto.name.length >= 5;

      if (this.nameError) {
        this.errorMessages.push('이름은 2자리이상 5자리 미만만 가능합니다.');
      }
    }
  }
}
</script>
<style scoped>
div {
  margin: 0.5rem 0;
}
</style>