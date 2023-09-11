<template>
  <div class="comment-section mt-4 bg-light">
    <table class="table">
      <tr>
        <td colspan="4" class="bg-light p-3">
          <textarea rows="2" class="form-control" placeholder="댓글을 입력해 주세요."
                    v-model="commentData.content"></textarea>
          <button @click="postComment" class="btn btn-primary mt-2">등록</button>
        </td>
      </tr>
      <tr class="border" v-for="(comment, index) in commentList" :key="index">
        <td colspan="4">
          <div
              class="d-flex justify-content-start font-weight-bold bg-light p-2">
            <span class="comment-writer">{{ comment.writer }}</span>
            <span class="text-muted"> &nbsp; {{ formatDate(comment.createdAt) }} &nbsp;</span>
            <div v-if="isAuthorized(comment)">
              <button class="btn btn-sm btn-danger"
                      @click="confirmDelete(comment.commentId)">삭제
              </button>
            </div>
          </div>
          <div class="comment-content text-start bg-light p-2">
            {{ comment.content }}
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import {deleteComment, postComment} from "@/api/freeBoardService";
import {formatDate} from "@/utils/dateUtil";

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
      commentData: {content: ''},
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
        await postComment(this.boardId, this.commentData);

        location.reload();
      } catch (error) {
        alert(error)
      }
    },
    /**
     * 댓글을 delete하는 메서드
     *
     * @returns {Promise<void>}
     */
    async deleteComment(commentId) {
      try {
        await deleteComment(commentId);

        location.reload();
      } catch (error) {
        alert(error)
      }
    },
    /**
     * 삭제 여부를 물어보는 메서드
     */
    confirmDelete(commentId) {
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.deleteComment(commentId);
      }
    },
    /**
     * 날짜를 포맷에 맞게 수정하는 메서드
     * @param date
     * @returns {string}
     */
    formatDate(date) {
      return formatDate(date);
    },
    /**
     * 댓글에 대한 권한이 있는지 여부
     * @param comment
     * @returns {boolean}
     */
    isAuthorized(comment) {
      const accountId = this.$store.getters.accountId;

      return comment.userId === accountId;
    },
  }
}
</script>

<style scoped>

</style>
