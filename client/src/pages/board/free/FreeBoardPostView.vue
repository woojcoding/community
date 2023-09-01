<template>
  <p class="h3 p-4 text-start">자유 게시판</p>
  <board-write-form
      :category-list-data="categoryListData"
      :type="'free'"
      @save="handleSave"
  ></board-write-form>
</template>

<script>
import BoardWriteForm from "@/components/board/BoardWriteForm";
import {postFreeBoard} from "@/api/freeBoardService";
import {loadCategoryList} from "@/api/categoryService";

export default {
  name: "FreeBoardPostView",
  components: {BoardWriteForm},
  data() {
    return {
      categoryListData: [],
    };
  },
  created() {
    this.loadCategoryListData();
  },
  methods: {
    /**
     * 카테고리 리스트를 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadCategoryListData() {
      try {
        const response = await loadCategoryList('free')

        this.categoryListData = response.data.categoryList;
      } catch (error) {
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
