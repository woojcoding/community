<template>
  <h3>공지 사항</h3>
  <search-form :category-list="categoryList"
               :board-search-condition="boardSearch"
               :type="type"
               @search-board="searchBoard"></search-form>
  <board-list :type="type"
              :notification-list="notificationList"
              :board-list="boardList"
              :total-board-count="totalBoardCount"
              :board-search-condition="boardSearch"></board-list>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import SearchForm from "@/components/board/SearchForm";
import {loadNoticeBoardList} from "@/api/noticeBoardService";
import {loadCategoryList} from "@/api/categoryService";
import dayjs from "dayjs";

export default {
  name: "NoticeBoardListView",
  components: {SearchForm, BoardList},
  props: {
    type: String,
  },
  data() {
    return {
      totalBoardCount: 0,
      notificationList: [],
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
    this.loadNoticeBoardList();
    this.loadCategoryList();

    if (Object.keys(this.$route.query).length > 0) {
      this.boardSearch = this.$route.query;
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

        this.totalBoardCount = response.data.totalBoardCount
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
        this.boardSearch = { ...boardSearch };

        const response = await loadNoticeBoardList(this.boardSearch);

        this.totalBoardCount = response.data.totalBoardCount

        this.boardList = response.data.boardList;

        this.notificationList = response.data.notificationList;

        this.$router.replace({
          query: this.boardSearch
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
