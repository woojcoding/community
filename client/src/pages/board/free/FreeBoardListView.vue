<template>
  <h3>자유 게시판</h3>
  <search-form :category-list="categoryList"
               :type="type"
               :board-search-condition="boardSearch"
               @search-board="searchBoard"></search-form>
  <button v-if="isLoggedIn" @click="moveToWriteForm">글등록</button>
  <board-list :type="type"
              :board-list="boardList"
              :total-board-count="totalBoardCount"
              :board-search-condition="boardSearch"></board-list>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import {loadFreeBoardList} from "@/api/freeBoardService";
import SearchForm from "@/components/board/SearchForm";
import {loadCategoryList} from "@/api/categoryService";
import dayjs from "dayjs";

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
      boardSearch: {
        startDate: dayjs().subtract(15, 'day').format('YYYY-MM-DD'),
        endDate: dayjs().add(15, 'day').format('YYYY-MM-DD'),
        category: 'all',
        keyword: '',
        pageNum: 1,
        pageSize: 10,
        sortBy: 'createdAt',
        sort: 'desc',
      },
    }
  },
  created() {
    this.loadFreeBoardList();
    this.loadCategoryList();

    if (Object.keys(this.$route.query).length > 0) {
      this.boardSearch = this.$route.query;
    }
  },
  methods: {
    /**
     * 자유게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadFreeBoardList() {
      try {
        const response = await loadFreeBoardList(this.boardSearch);

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
    },
    /**
     * 검색 조건에 따라 검색을 하는 메서드
     *
     * @param boardSearch
     * @returns {Promise<void>}
     */
    async searchBoard(boardSearch) {
      try {
        this.boardSearch = {...boardSearch};

        const response = await loadFreeBoardList(boardSearch);

        this.totalBoardCount = response.data.totalBoardCount

        this.boardList = response.data.boardList;

        this.$router.replace({
          query: this.boardSearch
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
