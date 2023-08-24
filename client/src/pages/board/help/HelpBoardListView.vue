<template>
  <h3>문의 게시판</h3>
  <search-form :category-list="categoryList"
               :board-search-condition="boardSearch"
               :type="type"
               @search-board="searchBoard"></search-form>
  <button v-if="isLoggedIn" @click="moveToWriteForm">글등록</button>
  <board-list :type="type"
              :board-list="boardList"
              :board-search-condition="boardSearch"
              :total-board-count="totalBoardCount"></board-list>
  <board-pagination :board-search-condition="boardSearch"
                    :total-board-count="totalBoardCount"
                    @search="searchBoard"></board-pagination>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import {loadHelpBoardList} from "@/api/helpBoardService";
import SearchForm from "@/components/board/SearchForm";
import dayjs from "dayjs";
import BoardPagination from "@/components/board/BoardPagination";

export default {
  name: "HelpBoardListView",
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
    if (Object.keys(this.$route.query).length > 0) {
      this.boardSearch = this.$route.query;
    }

    this.loadHelpBoardList();
  },
  methods: {
    /**
     * 문의 게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadHelpBoardList() {
      try {
        const response = await loadHelpBoardList(this.boardSearch);

        this.totalBoardCount = response.data.totalBoardCount
        this.boardList = response.data.boardList;
        this.categoryList = response.data.categoryList;

      } catch (error) {
        console.log(error);
        alert(error);
      }
    },
    /**
     * 작성폼으로 이동하는 메서드
     */
    moveToWriteForm() {
      this.$router.push({
        path: "/boards/help/post",
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
        this.boardSearch = { ...boardSearch };

        const response = await loadHelpBoardList(boardSearch);

        this.totalBoardCount = response.data.totalBoardCount

        this.boardList = response.data.boardList;

        this.$router.replace({
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
