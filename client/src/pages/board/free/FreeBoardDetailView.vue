<template>
  <p class="h3 p-4 text-start">자유 게시판</p>
  <div>
    <board-detail
        :board="boardData"
        :file-list="fileListData"
        @delete-board="deleteBoard"
    ></board-detail>
    <comment-form
        :comment-list="commentListData"
        :board-id="boardData.boardId">
    </comment-form>
  </div>
</template>

<script>
import BoardDetail from "@/components/board/BoardDetail";
import {deleteFreeBoard, loadFreeBoard} from "@/api/freeBoardService";
import CommentForm from "@/components/comment/CommentForm";

export default {
  name: "FreeBoardDetail",
  components: {CommentForm, BoardDetail},
  data() {
    return {
      boardData: {},
      fileListData: [],
      commentListData: [],
    };
  },
  created() {
    this.loadBoardData();
  },
  methods: {
    /**
     * 자유 게시글의 상세 정보를 가져오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadBoardData() {
      try {
        const boardId = this.$route.params.boardId;

        const response = await loadFreeBoard(boardId);

        this.boardData = response.data.board;
        this.fileListData = response.data.fileList;
        this.commentListData = response.data.commentList;
      } catch (error) {
        console.log(error);
        alert(error);
      }
    },
    /**
     * 게시글을 삭제하는 메서드
     *
     * @returns {Promise<void>}
     */
    async deleteBoard() {
      const boardId = this.$route.params.boardId

      try {
        const response = await deleteFreeBoard(boardId)

        alert(response.message);

        this.$router.push({
          path: `/boards/free/`,
          query: this.$route.query
        });
      } catch (error) {
        alert(error);
      }
    }
  },
};
</script>

<style scoped>

</style>
