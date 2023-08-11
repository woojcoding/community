<template>
  <div>
    <nav>
      <router-link to=""> 공지사항</router-link>
      |
      <router-link to=""> 자유 게시판</router-link>
      |
      <router-link to=""> 갤러리</router-link>
      |
      <router-link to=""> 문의 게시판</router-link>
    </nav>
    <div>
      <router-link to="/login"> 로그인</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "TheNavigator"
}
</script>

<style scoped>

</style>
