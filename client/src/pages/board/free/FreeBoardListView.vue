<template>
  <h2>자유 게시판</h2>
  <search-form></search-form>
  <button v-if="isLoggedIn" @click="moveToWriteForm">글등록</button>
  <board-list :type="type"
              :board-list="boardList"
              :total-board-count="totalBoardCount"
              :category-list="categoryList"></board-list>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import {loadFreeBoardList} from "@/api/freeBoardService";
import SearchForm from "@/components/board/SearchForm";
import {loadCategoryList} from "@/api/categoryService";

export default {
  name: "FreeBoardListView",
  components: {SearchForm, BoardList},
  props: {
    type: String,
  },
  computed: {
    isLoggedIn() {
      return this.$store.getters.isAuthenticated;
    },
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
    this.loadCategoryList();
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
      } catch (error) {
        console.log(error);
        alert(error);
      }
    },
    /**
     * 카테고리 리스트를 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadCategoryList() {
      try {
        const response = await loadCategoryList('free')

        this.categoryList = response.data.categoryList;
      } catch (error) {
        alert(error);
      }
    },
    /**
     * 작성폼으로 이동하는 메서드
     */
    moveToWriteForm() {
      this.$router.push({
        path: "/boards/free/post",
        query: this.$route.query
      });
    }
  },
};
</script>

<style scoped>

</style>
