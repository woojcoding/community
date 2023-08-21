<template>
  <h2>공지사항</h2>
  <search-form></search-form>
  <board-list :type="type"
              :notification-list="notificationList"
              :board-list="boardList"
              :total-board-count="totalBoardCount"
              :category-list="categoryList"></board-list>
</template>

<script>
import BoardList from "@/components/board/BoardList";
import SearchForm from "@/components/board/SearchForm";
import {loadNoticeBoardList} from "@/api/noticeBoardService";

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
    }
  },
  created() {
    this.loadNoticeBoardList();
  },
  methods: {
    /**
     * 공지 게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadNoticeBoardList() {
      try {
        const boardSearch = this.$route.query;
        const response = await loadNoticeBoardList(boardSearch);

        this.totalBoardCount = response.data.totalBoardCount
        this.boardList = response.data.boardList;
        this.notificationList = response.data.notificationList;
        this.categoryList = response.data.categoryList;

      } catch (error) {
        console.log(error);
        alert(error);
      }
    },
  }
}
</script>

<style scoped>

</style>
