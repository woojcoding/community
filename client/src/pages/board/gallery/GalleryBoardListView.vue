<template>
  <h3>갤러리</h3>
  <search-form :category-list="categoryList"
               :type="type"
               @search-board="searchBoard"></search-form>
  <div class="d-flex justify-content-end p-4">
    <button v-if="isLoggedIn" class="btn btn-primary" @click="moveToWriteForm">글등록</button>
  </div>
  <gallery-board-list :type="type"
                      :board-list="boardList"
                      :board-search-condition="boardSearch"></gallery-board-list>
  <board-pagination :board-search-condition="boardSearch"
                    :total-board-count="totalBoardCount"
                    @search="searchBoard"></board-pagination>
</template>

<script>
import {loadGalleryBoardList} from "@/api/galleryBoardService";
import SearchForm from "@/components/board/SearchForm";
import GalleryBoardList from "@/components/board/GalleryBoardList";
import {loadCategoryList} from "@/api/categoryService";
import dayjs from "dayjs";
import BoardPagination from "@/components/board/BoardPagination";

export default {
  name: "GalleryBoardListView",
  components: {BoardPagination, GalleryBoardList, SearchForm},
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
      totalBoardCount: 0,
    }
  },
  created() {
    if (this.$route.query && Object.keys(this.$route.query).length > 0) {
      this.boardSearch = {...this.$route.query};
    }

    this.loadGalleryBoardList();
    this.loadCategoryListData();
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
          };
        }

        this.loadGalleryBoardList();
      }
    }
  },
  methods: {
    /**
     * 자유게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadGalleryBoardList() {
      try {
        const response = await loadGalleryBoardList(this.boardSearch);

        this.boardList = response.data.boardList;
        this.totalBoardCount = response.data.totalBoardCount;
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
    async loadCategoryListData() {
      try {
        const response = await loadCategoryList('gallery')

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
        path: "/boards/gallery/post",
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
        this.$router.push({
          path: `/boards/gallery/`,
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
