<template>
  <div>
    <board-detail
        :board="boardData"
        :file-list="fileListData"
    ></board-detail>
    <comment-form
        :comment-list="commentListData">
    </comment-form>
  </div>
</template>

<script>
import BoardDetail from "@/components/board/BoardDetail";
import {loadFreeBoard} from "@/api/FreeBoardService";
import CommentForm from "@/components/comment/commentForm";

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
