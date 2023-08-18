<template>
  <div>
    <h2>게시판 - 보기</h2>
    <div class="board-details">
      <div class="board-header">
        <span class="category">{{ board.categoryName }}</span>
        <span class="title">{{ board.title }}</span>
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
    </div>
    <div class="buttons">
      <button @click="list">목록</button>
      <button @click="moveToModifyForm">수정</button>
      <button>삭제</button>
    </div>
  </div>
</template>

<script>
import {downloadFile} from "@/api/FreeBoardService";

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
     * 수정폼으로 이동하는 메서드
     */
    moveToModifyForm() {
      const boardId = this.board.boardId;

      this.$router.push({
        path: `/boards/free/modify/${boardId}`,
        query: this.$route.query
      });
    }
  }
}
</script>

<style scoped>

</style>
