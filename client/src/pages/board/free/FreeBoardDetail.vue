<template>
  <div>
    <board-detail
        :board="boardData"
        :file-list="fileListData"
        :type="'free'"
    ></board-detail>
    <comment-form
        :comment-list="commentListData"
        :board-id="boardData.boardId">
    </comment-form>
  </div>
</template>

<script>
import BoardDetail from "@/components/board/BoardDetail";
import {loadFreeBoard} from "@/api/freeBoardService";
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
  },
};
</script>

<style scoped>

</style>
