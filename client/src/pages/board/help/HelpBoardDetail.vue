<template>
  <div>
    <board-detail
        :board="boardData"
        :type="'help'"
    ></board-detail>
  </div>
</template>

<script>
import BoardDetail from "@/components/board/BoardDetail";
import {loadHelpBoard} from "@/api/helpBoardService";

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
     * 자유 게시글의 상세 정보를 가져오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadBoardData() {
      try {
        const boardId = this.$route.params.boardId;

        const response = await loadHelpBoard(boardId);

        this.boardData = response.data.board;
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
