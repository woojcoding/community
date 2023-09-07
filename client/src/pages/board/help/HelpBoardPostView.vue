<template>
  <p class="h3 p-4 text-start">문의 게시판</p>
  <board-write-form
      :require-title-tr="true"
      :require-question-tr="true"
      :require-secret-option-tr="true"
      @save="handleSave"
  ></board-write-form>
</template>

<script>
import BoardWriteForm from "@/components/board/BoardWriteForm";
import {postHelpBoard} from "@/api/helpBoardService";

export default {
  name: "HelpBoardPostView",
  components: {BoardWriteForm},
  methods: {
    /**
     * 게시글 post를 axios 요청하는 메서드
     *
     * @param board
     * @returns {Promise<void>}
     */
    async handleSave(board) {
      try {
        const response = await postHelpBoard(board);

        const boardId = response.data.boardId;

        this.$router.push(`/boards/help/${boardId}`);
      } catch (error) {
        alert(error);
      }
    }
  },
};
</script>

<style scoped>

</style>
