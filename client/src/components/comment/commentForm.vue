<template>
  <table>
    <textarea rows="2" cols="250" placeholder="댓글을 입력해 주세요."
              v-model="f"></textarea>
    <button @click="postComment">등록</button>
    <tr v-for="(comment, index) in commentList" :key="index">
      <td colspan="4">
        <span>{{ comment.writer }}</span>
        <span>{{ comment.createdAt }}</span>
        <br>
        <span>{{ comment.content }}</span>
      </td>
    </tr>
  </table>
</template>

<script>
export default {
  name: "commentForm",
  props: {
    commentList: {
      type: Array,
      default: undefined,
      required: true,
      description: '댓글 리스트'
    },
  },
  data() {
    return {
      f: "",
    };
  }
}
</script>

<style scoped>

</style>
