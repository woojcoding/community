<template>
  <h3>자유 게시판</h3>
  <search-form :category-list="categoryList"
               :type="type"
               @search-board="searchBoard"></search-form>
  <div class="d-flex justify-content-end p-4">
    <button v-if="isLoggedIn" class="btn btn-primary" @click="moveToWriteForm">글등록</button>
  </div>
  <board-list :type="type"
              :board-list="boardList"
              :total-board-count="totalBoardCount"
              :board-search-condition="boardSearch"></board-list>
  <board-pagination :board-search-condition="boardSearch"
                    :total-board-count="totalBoardCount"
                    @search="searchBoard"></board-pagination>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import {loadFreeBoardList} from "@/api/freeBoardService";
import SearchForm from "@/components/board/SearchForm";
import {loadCategoryList} from "@/api/categoryService";
import dayjs from "dayjs";
import BoardPagination from "@/components/board/BoardPagination";

export default {
  name: "FreeBoardListView",
  components: {BoardPagination, SearchForm, BoardList},
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
        startDate: dayjs().subtract(30, 'day').format('YYYY-MM-DD'),
        endDate: dayjs().format('YYYY-MM-DD'),
        category: 'all',
        keyword: '',
        pageNum: 1,
        pageSize: 10,
        sortBy: 'createdAt',
        sort: 'desc',
      },
    }
  },
  watch: {
    $route(to, from) {
      if (to.query !== from.query) {
        this.boardSearch = {
          ...this.boardSearch,
          ...to.query
        };

        if (Object.keys(to.query).length === 0) {
          this.boardSearch = {
            startDate: dayjs().subtract(30, 'day').format('YYYY-MM-DD'),
            endDate: dayjs().format('YYYY-MM-DD'),
            category: 'all',
            keyword: '',
            pageNum: 1,
            pageSize: 10,
            sortBy: 'createdAt',
            sort: 'desc',
          };
        }

        this.loadFreeBoardList();
      }
    }
  },
  created() {
    if (this.$route.query && Object.keys(this.$route.query).length > 0) {
      this.boardSearch = {...this.$route.query};
    }

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
    searchBoard(boardSearch) {
      try {
        this.$router.push({
          path: `/boards/free/`,
          query: boardSearch
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
