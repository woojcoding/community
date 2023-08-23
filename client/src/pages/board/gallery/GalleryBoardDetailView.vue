<template>
  <h3>갤러리</h3>
  <div>
    <board-detail
        :board="boardData"
        :file-list="fileListData"
        :type="'gallery'"
        @delete-board="deleteBoard"
    ></board-detail>
  </div>
</template>

<script>
import BoardDetail from "@/components/board/BoardDetail";
import {deleteGalleryBoard, loadGalleryBoard} from "@/api/galleryBoardService";

export default {
  name: "GalleryBoardDetailView",
  components: {BoardDetail},
  data() {
    return {
      boardData: {},
      fileListData: []
    };
  },
  created() {
    this.loadBoardData();
  },
  methods: {
    /**
     * 갤러리 게시글의 상세 정보를 가져오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadBoardData() {
      try {
        const boardId = this.$route.params.boardId;

        const response = await loadGalleryBoard(boardId);

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
        const response = await deleteGalleryBoard(boardId);

        alert(response.message);

        this.$router.push({
          path: `/boards/gallery/`,
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
