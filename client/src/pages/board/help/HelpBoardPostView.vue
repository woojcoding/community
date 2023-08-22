<template>
  <board-write-form
      :type="'help'"
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
