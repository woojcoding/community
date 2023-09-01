<template>
  <p class="h3 p-4 text-start">공지사항</p>
  <search-form :category-list="categoryList"
               :type="type"
               @search-board="searchBoard"></search-form>
  <board-list :type="type"
              :notification-list="notificationList"
              :board-list="boardList"
              :total-board-count="totalBoardCount"
              :board-search-condition="boardSearch"></board-list>
  <board-pagination :board-search-condition="boardSearch"
                    :total-board-count="totalBoardCount"
                    @search="searchBoard"></board-pagination>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import SearchForm from "@/components/board/SearchForm";
import {loadNoticeBoardList} from "@/api/noticeBoardService";
import {loadCategoryList} from "@/api/categoryService";
import dayjs from "dayjs";
import BoardPagination from "@/components/board/BoardPagination";

export default {
  name: "NoticeBoardListView",
  components: {BoardPagination, SearchForm, BoardList},
  props: {
    type: String,
  },
  data() {
    return {
      totalBoardCount: 1,
      notificationList: [],
      boardList: [],
      categoryList: [],
      boardSearch: {
        startDate: dayjs().subtract(15, 'day').format('YYYY-MM-DD'),
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
  created() {
    if (this.$route.query && Object.keys(this.$route.query).length > 0) {
      this.boardSearch = {...this.$route.query};
    }

    this.loadNoticeBoardList();
    this.loadCategoryList();
  },
  watch: {
    /**
     * 라우트의 쿼리 변경을 감지하여 boardSearch의 데이터를 변경 후 BoardList의 데이터를 다시 가져오는 메서드
     * @param to
     * @param from
     */
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
            displayMyPostsOnly: false,
          };
        }

        this.loadNoticeBoardList();
      }
    }
  },
  methods: {
    /**
     * 공지 게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadNoticeBoardList() {
      try {
        const response = await loadNoticeBoardList(this.boardSearch);
        if (response.data.totalBoardCount !== 0) {
          this.totalBoardCount = response.data.totalBoardCount
        }
        this.boardList = response.data.boardList;
        this.notificationList = response.data.notificationList;

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
        const response = await loadCategoryList('notice')

        this.categoryList = response.data.categoryList;
      } catch (error) {
        alert(error);
      }
    },
    /**
     * 검색 조건에 따라 검색을 하는 메서드
     *
     * @param boardSearch
     * @returns {Promise<void>}
     */
    async searchBoard(boardSearch) {
      try {
        this.$router.push({
          path: `/boards/notice/`,
          query: boardSearch
        });
      } catch (error) {
        alert(error);
      }
    }
  }
}
</script>

<style scoped>

</style>
