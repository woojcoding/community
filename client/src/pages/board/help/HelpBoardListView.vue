<template>
  <h3>문의 게시판</h3>
  <search-form></search-form>
  <button v-if="isLoggedIn" @click="moveToWriteForm">글등록</button>
  <board-list :type="type"
              :board-list="boardList"
              :total-board-count="totalBoardCount"></board-list>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import {loadHelpBoardList} from "@/api/helpBoardService";
import SearchForm from "@/components/board/SearchForm";

export default {
  name: "HelpBoardListView",
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
    }
  },
  created() {
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
        const boardSearch = this.$route.query;
        const response = await loadHelpBoardList(boardSearch);

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
    }
  },
};
</script>

<style scoped>

</style>
