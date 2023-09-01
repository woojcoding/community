<template>
  <p class="h3 p-4 text-start">문의 게시판</p>
  <board-write-form
      :board-data="boardData"
      :type="'help'"
      v-if="boardData.boardId"
      @save="handleSave"
  ></board-write-form>
</template>

<script>
import BoardWriteForm from "@/components/board/BoardWriteForm";
import {loadHelpBoardForModify, patchHelpBoard} from "@/api/helpBoardService";

export default {
  name: "HelpBoardModifyView",
  components: {BoardWriteForm},
  data() {
    return {
      boardData: {},
    };
  },
 created() {
    this.loadBoardData();
  },
  methods: {
    /**
     * 문의 게시글의 상세 정보를 가져오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadBoardData() {
      try {
        const boardId = this.$route.params.boardId;

        const response = await loadHelpBoardForModify(boardId);

        this.boardData = response.data.board;
      } catch (error) {
        alert(error);
      }
    },
    /**
     * 게시글 수정사항을 axios 요청하는 메서드
     *
     * @param board
     * @returns {Promise<void>}
     */
    async handleSave(board) {
      try {
        const boardId = this.$route.params.boardId;

        await patchHelpBoard(board, boardId);

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
