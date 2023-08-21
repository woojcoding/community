<template>
  <table>
    <textarea rows="2" cols="250" placeholder="댓글을 입력해 주세요."
              v-model="comment.content"></textarea>
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
import {postComment} from "@/api/FreeBoardService";

export default {
  name: "CommentForm",
  props: {
    commentList: {
      type: Array,
      default: undefined,
      required: true,
      description: '댓글 리스트'
    },
    boardId: {
      type: Number,
      default: 0,
      required: true,
      description: '게시글 Id'
    }
  },
  data() {
    return {
      comment: {content: ''},
    };
  },
  methods: {
    /**
     * 댓글을 post하는 메서드
     *
     * @returns {Promise<void>}
     */
    async postComment() {
      try {
        await postComment(this.boardId, this.comment);

        location.reload();
      } catch (error) {
        alert(error)
      }
    }
  }
}
</script>

<style scoped>

</style>
