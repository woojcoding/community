<template>
  <p class="h3 p-4 text-start">문의 게시판</p>
  <div>
    <board-detail
        :board="boardData"
        :type="'help'"
        @delete-board="deleteBoard"
    ></board-detail>
  </div>
</template>

<script>
import BoardDetail from "@/components/board/BoardDetail";
import {deleteHelpBoard, loadHelpBoard} from "@/api/helpBoardService";

export default {
  name: "HelpBoardDetail",
  components: {BoardDetail},
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

        const response = await loadHelpBoard(boardId);

        this.boardData = response.data.board;
      } catch (error) {
        this.$router.go(-1);
        
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
        const response = await deleteHelpBoard(boardId)

        alert(response.message);

        this.$router.push({
          path: `/boards/help/`,
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
