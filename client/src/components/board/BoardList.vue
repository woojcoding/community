<template>
  <div>
    <table class="table border-bottom">
      <thead>
      <tr>
        <th scope="col">번호</th>
        <th v-if="showCategory" scope="col">분류</th>
        <th scope="col">제목</th>
        <th scope="col">조회수</th>
        <th scope="col">등록 일시</th>
        <th scope="col">작성자</th>
      </tr>
      </thead>
      <tbody>
      <!-- notificationList 데이터가 있는 경우에만 랜더링 -->
      <template v-if="notificationList">
        <tr v-for="notification in notificationList"
            :key="notification.boardId">
          <td></td>
          <td>알림</td>
          <td class="text-start">
            <a @click="boardDetail(notification.boardId)">
              {{ notification.title }}
            </a>
          </td>
          <td>{{ notification.views }}</td>
          <td>{{ formatDate(notification.createdAt) }}</td>
          <td>{{ notification.writer }}</td>
        </tr>
      </template>
      <tr v-for="(board, index) in boardList" :key="board.boardId">
        <td>{{ calculatedNumber(index) }}</td>
        <td v-if="showCategory">{{ board.categoryName }}</td>
        <td class="text-start">
          <a @click="boardDetail(board.boardId)">
            {{ truncateTitle(board.title) }}
            <span v-if="showAnswerStatus && board.answer">(답변완료)</span>
            <span v-if="showAnswerStatus && !board.answer">(미답변)</span>
            <span v-if="board.secretFlag">
                <i class="fas fa-lock ms-2"></i>
            </span>
            <span v-if="showCommentCount" class="ms-1">
                ({{ board.commentCount }})
            </span>
            <span v-if="board.hasAttached">
              <i class="fas fa-paperclip ms-2"></i>
            </span>
            <span v-if="isNew(board.createdAt)"
                  class="new-label ms-2">new
            </span>
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
    showCategory: {
      type: Boolean,
      default: true,
      required: false,
      description: '카테고리 보여줄 필요 여부'
    },
    showAnswerStatus: {
      type: Boolean,
      default: false,
      required: false,
      description: '답변 상태를 보여줄 필요의 여부'
    },
    showCommentCount: {
      type: Boolean,
      default: false,
      required: false,
      description: '댓글 개수를 보여줄 필요의 여부'
    },
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
      const pathArray = this.$route.path.split('/');

      const typeIndex = pathArray.indexOf('boards') + 1;

      if (typeIndex > 0 && typeIndex < pathArray.length) {
        const type = pathArray[typeIndex];

        const path = `/boards/${type}`;

        this.$router.push({
          path: `${path}/${boardId}`,
          query: this.$route.query
        });
      }
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
