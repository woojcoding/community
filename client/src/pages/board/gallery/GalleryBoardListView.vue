<template>
  <h2>자유 게시판</h2>
  <search-form></search-form>
  <button v-if="isLoggedIn" @click="moveToWriteForm">글등록</button>
  <gallery-board-list :type="type"
                      :board-list="boardList"
                      :category-list="categoryList"></gallery-board-list>
</template>

<script>
import {loadGalleryBoardList, loadThumbnail} from "@/api/galleryBoardService";
import SearchForm from "@/components/board/SearchForm";
import GalleryBoardList from "@/components/board/GalleryBoardList";

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
    }
  },
  created() {
    this.loadGalleryBoardList();
  },
  methods: {
    /**
     * 자유게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadGalleryBoardList() {
      try {
        const boardSearch = this.$route.query;
        const response = await loadGalleryBoardList(boardSearch);

        this.boardList = response.data.boardList;
        this.categoryList = response.data.categoryList;

        for (const board of this.boardList) {
          this.loadThumbnailUrl(board);
        }
      } catch (error) {
        console.log(error);
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
    }
  },
};
</script>

<style scoped>

</style>
