<template>
  <div>
    <table class="table border-bottom">
      <thead>
      <tr>
        <th scope="col">번호</th>
        <th v-if="type !== 'help'" scope="col">분류</th>
        <th scope="col">제목</th>
        <th scope="col">조회수</th>
        <th scope="col">등록 일시</th>
        <th scope="col">작성자</th>
      </tr>
      </thead>
      <tbody>
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
            {{ truncateTitle(board.title) }}
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
            <span v-if="isNew(board.createdAt)" class="new-label">new</span>
          </a>
        </td>
        <td>{{ board.views }}</td>
        <td>{{ formatDate(board.createdAt) }}</td>
        <td>{{ board.writer }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {formatDate, isNew} from "@/utils/dateUtil";


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
    boardSearchCondition: {
      type: Object,
      default: undefined,
      required: true,
      description: '검색 조건'
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
      return index => this.totalBoardCount - ((this.boardSearchCondition.pageNum - 1) * this.boardSearchCondition.pageSize) - index;
    }
  },
  methods: {
    /**
     * 게시글 상세보기 페이지로 이동하는 메서드
     *
     * @param boardId
     */
    boardDetail(boardId) {
      this.$router.push({
        path: `/boards/${this.type}/${boardId}`,
        query: this.$route.query
      });
    },
    /**
     * 날짜를 포맷에 맞게 수정하는 메서드
     * @param date
     * @returns {string}
     */
    formatDate(date) {
      return formatDate(date);
    },
    /**
     * New 표시 여부를 결정해주는 메서드
     * @param createdAt
     * @returns {boolean}
     */
    isNew(createdAt) {
      return isNew(createdAt);
    },
    /**
     * 제목 길이를 제한하는 메서드
     * @param title
     * @returns {string|*}
     */
    truncateTitle(title) {
      const maxLength = 20;

      if (title.length <= maxLength) {
        return title;
      } else {
        return title.slice(0, maxLength) + "...";
      }
    },
  }
}
</script>

<style scoped>
.new-label {
  font-weight: bold;
  color: red;
  margin-left: 5px;
}
</style>
