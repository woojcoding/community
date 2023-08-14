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
      <tr v-if="type === 'notice' && notificationList">
        <td></td>
        <td>알림</td>
        <td v-for="notification in notificationList"
            :key="notification.boardId">
          <a
              :href="generateLink(notification)"
              style="text-decoration:none;"
          >{{ notification.title }}</a>
        </td>
      </tr>
      <tr v-for="(board, index) in boardList" :key="board.boardId">
        <td>{{ calculatedNumber(index) }}</td>
        <td v-if="type !== 'help'">{{ board.categoryName }}</td>
        <td>
          <a>
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
        pageNum: 1, // Default pageNum
        pageSize: 10, // Default pageSize
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
    calculatedNumber() {
      return index => this.totalBoardCount - ((this.boardSearch.pageNum - 1) * this.boardSearch.pageSize) - index + 1;
    }
  },
};
</script>

<style scoped>

</style>
