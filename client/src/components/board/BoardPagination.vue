<template>
  <div class="pagination">
    <a v-if="currentPage > 1" @click="changePage(1)">&lt;&lt;</a>
    <a v-if="currentPage > 1"
       @click="changePage(Math.max(currentPage - 1, 1))">&lt;</a>
    <a v-for="page in pages" :key="page" @click="changePage(page)">
      {{ page }}
    </a>
    <a v-if="currentPage < pageCount"
       @click="changePage(Math.min(currentPage + 1, pageCount))">&gt;</a>
    <a v-if="currentPage < pageCount"
       @click="changePage(pageCount)">&gt;&gt;</a>
  </div>
</template>

<script>
export default {
  name: "BoardPagination",
  props: {
    boardSearchCondition: {
      type: Object,
      default: undefined,
      required: true,
      description: "검색 조건",
    },
    totalBoardCount: {
      type: Number,
      default: undefined,
      required: true,
      description: '총 게시글 수'
    },
  },
  emits: ['search'],
  created() {
    if (this.boardSearchCondition) {
      this.boardSearch = {...this.boardSearchCondition};
      this.currentPage = this.boardSearch.pageNum;
    }
  },
  computed: {
    /**
     * 페이지의 수를 계산
     * @returns {number}
     */
    pageCount() {
      return Math.ceil(this.totalBoardCount / this.boardSearch.pageSize);
    },
    /**
     * 시작 페이지를 계산
     * @returns {number}
     */
    computedStartPage() {
      return Math.floor((this.currentPage - 1) / this.pageLimit) * this.boardSearch.pageSize + 1;
    },
    /**
     * 마지막 페이지를 계산
     * @returns {number}
     */
    computedEndPage() {
      const endPage = this.computedStartPage + this.pageLimit - 1;
      return Math.min(endPage, this.pageCount);
    },
    /**
     * 페이지 숫자들을 생성함
     * @returns {*[]}
     */
    pages() {
      const pages = [];
      for (let i = this.computedStartPage; i <= this.computedEndPage; i++) {
        pages.push(i);
      }
      return pages;
    },
  },
  data() {
    return {
      currentPage: 1,
      boardSearch: {},
      pageLimit: 10,
    };
  },
  methods: {
    /**
     * 페이지 변경시 emit을 하는 메서드
     * @param page
     */
    changePage(page) {
      this.boardSearch.pageNum = page;
      this.$emit('search', this.boardSearch);
    },
  },
};
</script>

<style scoped>

</style>
