<template>
  <board-write-form
      :board-data="boardData"
      :file-list-data="fileListData"
      :category-list-data="categoryListData"
      :type="'free'"
      v-if="boardData.boardId"
      @save="handleSave"
  ></board-write-form>
</template>

<script>
import BoardWriteForm from "@/components/board/BoardWriteForm";
import {
  loadFreeBoardCategory,
  loadFreeBoardForModify,
  patchFreeBoard
} from "@/api/FreeBoardService";

export default {
  name: "FreeBoardModifyView",
  components: {BoardWriteForm},
  data() {
    return {
      boardData: {},
      fileListData: [],
      categoryListData: [],
    };
  },
 created() {
    this.loadBoardData();
    this.loadCategoryData();
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

        const response = await loadFreeBoardForModify(boardId);

        this.boardData = response.data.board;
        this.fileListData = response.data.fileList;
      } catch (error) {
        console.log(error);
        alert(error);
      }
    },
    async loadCategoryData() {
      try {
        const response = await loadFreeBoardCategory();

        this.categoryListData = response.data.categoryList;
      } catch (error) {
        console.log(error);
        alert(error);
      }
    },
    /**
     * 게시글과 파일 정보를 폼데이터로 만들어 axios 요청하는 메서드
     *
     * @param board
     * @param files
     * @returns {Promise<void>}
     */
    async handleSave(board, files, deleteFileIds) {
      const formData = new FormData();

      formData.append('categoryId', board.categoryId);
      formData.append('title', board.title);
      formData.append('content', board.content);

      //각각의 선택된 파일들을 formData에 직접 추가
      for (let i = 0; i < files.length; i++) {
        formData.append(`files[${i}]`, files[i]);
      }

      //각각의 삭제할 파일 아이디들을 formData에 직접 추가
      for (let i = 0; i < deleteFileIds.length; i++) {
        formData.append(`deleteFileIdList[${i}]`, deleteFileIds[i]);
      }

      try {
        const boardId = this.$route.params.boardId;

        await patchFreeBoard(formData, boardId);

        this.$router.push(`/boards/free/${boardId}`);
      } catch (error) {
        alert(error);
      }
    }
  },
};
</script>

<style scoped>

</style>
