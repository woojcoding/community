<template>
  <div>
    <table class="boardList">
      <tr>
        <td>번호</td>
        <td v-if="type !== 'help'">분류</td>
        <td>제목</td>
        <td>조회수</td>
        <td>등록 일시</td>
        <td>작성자</td>
      </tr>
      <!-- notice 인 경우에만 랜더링 -->
      <template v-if="type === 'notice' && notificationList">
        <tr v-for="notification in notificationList" :key="notification.boardId">
          <td></td>
          <td>알림</td>
          <td>
            <a @click="boardDetail(notification.boardId)">
              {{ notification.title }}
            </a>
          </td>
          <td>{{ notification.views }}</td>
          <td>{{ notification.createdAt }}</td>
          <td>{{ notification.writer }}</td>
        </tr>
      </template>
      <tr v-for="(board, index) in boardList" :key="board.boardId">
        <td>{{ calculatedNumber(index) }}</td>
        <td v-if="type !== 'help'">{{ board.categoryName }}</td>
        <td>
          <a @click="boardDetail(board.boardId)">
            {{ board.title }}
            <span v-if="type === 'help' && board.answer">(답변완료)</span>
            <span v-if="type === 'help' && !board.answer">(미답변)</span>
            <span v-if="type === 'help' && board.secretFlag">
              <i class="fas fa-lock"></i>
            </span>
            <span v-if="type === 'free'">
              ({{ board.commentCount }})
              <span v-if="board.isAttached">
                <i class="fas fa-paperclip"></i>
              </span>
            </span>
          </a>
        </td>
        <td>{{ board.views }}</td>
        <td>{{ board.createdAt }}</td>
        <td>{{ board.writer }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "BoardList",
  props: {
    notificationList: {
      type: Array,
      default: undefined,
      required: false,
      description: '알림글 리스트'
    },
    boardList: {
      type: Array,
      default: undefined,
      required: true,
      description: '게시글 리스트'
    },
    boardSearch: {
      type: Object,
      default: () => ({
        startDate: '',
        endDate: '',
        category: '',
        keyword: '',
        pageNum: 1,
        pageSize: 10,
        sort: '',
        offSet: 0,
        boardType: ''
      }),
      required: false,
      description: '검색 조건'
    },
    categoryList: {
      type: Object,
      default: undefined,
      required: true,
      description: '카테고리 리스트'
    },
    totalBoardCount: {
      type: Number,
      default: undefined,
      required: true,
      description: '총 게시글 수'
    },
    type: {
      type: String,
      default: undefined,
      required: true,
      description: '게시글 타입'
    }
  },
  computed: {
    /**
     * 게시글의 번호를 계산
     *
     * @returns {function(*)}
     */
    calculatedNumber() {
      return index => this.totalBoardCount - ((this.boardSearch.pageNum - 1) * this.boardSearch.pageSize) - index + 1;
    }
  },
  methods: {
    /**
     * 게시글 상세보기 페이질로 이동하는 메서드
     *
     * @param boardId
     */
    boardDetail(boardId) {
      this.$router.push({
        path: `/boards/${this.type}/${boardId}`,
        query: {
          ...this.boardSearch,
          boardId: boardId
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
