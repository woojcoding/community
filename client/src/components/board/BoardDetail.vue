<template>
  <div>
    <h2>게시판 - 보기</h2>
    <div class="board-details">
      <div class="board-header">
        <span class="category">{{ board.categoryName }}</span>
        <span class="title">{{ board.title }}</span>
        <span v-if="type === 'help' && board.answer">(답변완료)</span>
        <span v-if="type === 'help' && !board.answer">(미답변)</span>
        <span class="info">{{ board.createdAt }} {{ board.writer }}</span>
      </div>
      <div class="views">
        조회수: {{ board.views }}
      </div>
      <div class="content">
        {{ board.content }}
      </div>
      <div class="files">
        <div v-for="file in fileList" :key="file.fileId">
          <a @click="downloadFile(file.fileId)">
            <span>{{ file.originalName }}</span>
          </a>
        </div>
      </div>
      <div class="answer" v-if="type === 'help'">
        <template v-if="board.answer">
          <div class="info">
            {{ board.answerer }} {{ board.answeredAt }}
          </div>
          <div>
            <span class="answer">{{ board.answer }}</span>
          </div>
        </template>
        <template v-else>
          <div class="no-answer">
            아직 등록된 답변이 없습니다.
          </div>
        </template>
      </div>
      <div class="buttons">
        <button @click="moveToList">목록</button>
        <button v-if="isAuthorized" @click="moveToModifyForm">수정</button>
        <button v-if="isAuthorized" @click="confirmDelete">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
import {deleteFreeBoard, downloadFile} from "@/api/freeBoardService";

export default {
  name: "BoardDetail",
  data() {
    return {
      modalOpen: false,
    }
  },
  props: {
    board: {
      type: Object,
      default: undefined,
      required: true,
      description: '게시글'
    },
    fileList: {
      type: Array,
      default: undefined,
      required: false,
      description: '파일 리스트'
    },
    type: {
      type: String,
      default: undefined,
      required: false,
      description: '게시글 타입'
    }
  },
  computed: {
    isAuthorized() {
      const accountId = this.$store.getters.accountId;

      return this.board.userId === accountId;
    },
  },
  methods: {
    /**
     * 파일을 다운르도하는 메서드
     *
     * @param fileId
     * @returns {Promise<void>}
     */
    async downloadFile(fileId) {
      try {
        await downloadFile(fileId);
      } catch (error) {
        alert(error);
      }
    },
    /**
     * 목록으로 이동하는 메서드
     */
    moveToList() {
      this.$router.push({
        path: `/boards/free/`,
        query: this.$route.query
      });
    },
    /**
     * 수정폼으로 이동하는 메서드
     */
    moveToModifyForm() {
      const boardId = this.board.boardId;

      this.$router.push({
        path: `/boards/free/modify/${boardId}`,
        query: this.$route.query
      });
    },
    /**
     * 삭제 여부를 물어보는 메서드
     */
    confirmDelete() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.deleteBoard();
      }
    },
    /**
     * 게시글을 삭제하는 메서드
     *
     * @returns {Promise<void>}
     */
    async deleteBoard() {
      const boardId = this.board.boardId;

      try {
        const response = await deleteFreeBoard(boardId)

        alert(response.message);

        this.moveToList();
      } catch (error) {
        alert(error);
      }
    }
  }
}
</script>

<style scoped>

</style>
