<template>
  <h2>자유 게시판</h2>
  <search-form></search-form>
  <board-list :type="type"
              :board-list="boardList"
              :total-board-count="totalBoardCount"
              :category-list="categoryList"></board-list>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import {loadFreeBoardList} from "@/api/FreeBoardService";
import SearchForm from "@/components/board/SearchForm";

export default {
  name: "FreeBoardListView",
  components: {SearchForm, BoardList},
  props: {
    type: String,
  },
  data() {
    return {
      totalBoardCount: 0,
      boardList: [],
      categoryList: [],
    }
  },
  created() {
    this.loadFreeBoardList();
  },
  methods: {
    /**
     * 자유게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadFreeBoardList() {
      try {
        const boardSearch = this.$route.query;
        const response = await loadFreeBoardList(boardSearch);

        this.totalBoardCount = response.data.totalBoardCount
        this.boardList = response.data.boardList;
        this.categoryList = response.data.categoryList;

      } catch (error) {
        console.log(error);
        alert(error);
      }
    }
  }
};
</script>

<style scoped>

</style>
