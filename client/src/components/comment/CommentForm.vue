<template>
  <div class="comment-section">
    <table class="table">
      <tr>
        <td colspan="4">
          <textarea rows="2" class="form-control" placeholder="댓글을 입력해 주세요."
                    v-model="comment.content"></textarea>
          <button @click="postComment" class="btn btn-primary mt-2">등록</button>
        </td>
      </tr>
      <tr v-for="(comment, index) in commentList" :key="index">
        <td colspan="4">
          <div class="d-flex justify-content-between align-items-center font-weight-bold">
            <span class="comment-writer">{{ comment.writer }}</span>
            <span class="text-muted">{{ comment.createdAt }}</span>
          </div>
          <div class="comment-content mt-2">
            {{ comment.content }}
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import {postComment} from "@/api/freeBoardService";

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
