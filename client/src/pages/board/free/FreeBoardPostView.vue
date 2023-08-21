<template>
  <board-write-form
      :category-list-data="categoryListData"
      :type="'free'"
      @save="handleSave"
  ></board-write-form>
</template>

<script>
import BoardWriteForm from "@/components/board/BoardWriteForm";
import {loadFreeBoardCategory, postFreeBoard} from "@/api/freeBoardService";

export default {
  name: "FreeBoardModifyView",
  components: {BoardWriteForm},
  data() {
    return {
      categoryListData: [],
    };
  },
  created() {
    this.loadCategoryData();
  },
  methods: {
    /**
     * 카테고리 정보를 axios 요청으로 가져오는 메서드
     *
     * @returns {Promise<void>}
     */
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
    async handleSave(board, files) {
      const formData = new FormData();

      formData.append('categoryId', board.categoryId);
      formData.append('title', board.title);
      formData.append('content', board.content);

      //각각의 선택된 파일들을 formData에 직접 추가
      for (let i = 0; i < files.length; i++) {
        formData.append(`files[${i}]`, files[i]);
      }

      try {
        const response = await postFreeBoard(formData);

        const boardId = response.data.boardId;

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
