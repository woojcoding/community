<template>
  <div>
    <p class="h3 p-4 text-start">공지사항</p>
    <board-detail
        :board="boardData"
    ></board-detail>
  </div>
</template>

<script>
import BoardDetail from "@/components/board/BoardDetail";
import {loadNoticeBoard} from "@/api/noticeBoardService";

export default {
  name: "NoticeBoardDetailView",
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
     * 공지 게시글의 상세 정보를 가져오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadBoardData() {
      try {
        const boardId = this.$route.params.boardId;

        const response = await loadNoticeBoard(boardId);

        this.boardData = response.data.board;
      } catch (error) {
        alert(error);
      }
    },
  },
};
</script>

<style scoped>

</style>
