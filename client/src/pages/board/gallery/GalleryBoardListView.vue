<template>
  <h3>갤러리</h3>
  <search-form :category-list="categoryList"
               :board-search-condition="boardSearch"
               :type="type"
               @search-board="searchBoard"></search-form>
  <button v-if="isLoggedIn" @click="moveToWriteForm">글등록</button>
  <gallery-board-list :type="type"
                      :board-list="boardList"
                      :board-search-condition="boardSearch"></gallery-board-list>
</template>

<script>
import {loadGalleryBoardList, loadThumbnail} from "@/api/galleryBoardService";
import SearchForm from "@/components/board/SearchForm";
import GalleryBoardList from "@/components/board/GalleryBoardList";
import {loadCategoryList} from "@/api/categoryService";
import dayjs from "dayjs";

export default {
  name: "GalleryBoardListView",
  components: {GalleryBoardList, SearchForm},
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
    this.loadGalleryBoardList();
    this.loadCategoryListData();

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
    async loadGalleryBoardList() {
      try {
        const response = await loadGalleryBoardList(this.boardSearch);

        this.boardList = response.data.boardList;

        for (const board of this.boardList) {
          await this.loadThumbnailUrl(board);
        }
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
     * 썸네일을 불러와 board에 지정해주는 메서드
     *
     * @param board
     * @returns {Promise<void>}
     */
    async loadThumbnailUrl(board) {
      try {
        const response = await loadThumbnail(board.boardId);

        board.thumbnailUrl = response.data.url;
      } catch (error) {
        console.log(error);
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
        this.boardSearch = {...boardSearch};

        const response = await loadGalleryBoardList(boardSearch);

        this.totalBoardCount = response.data.totalBoardCount

        this.boardList = response.data.boardList;

        for (const board of this.boardList) {
          await this.loadThumbnailUrl(board);
        }

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
