<template>
  <div>
    <div class="board-details mt-4">
      <div class="row justify-content-start border-bottom pb-2">
        <div v-if="showCategory" class="col-md-1">
          {{ board.categoryName }}
        </div>
        <div v-if="showAnswerStatus" class="col-md-9 text-start">
          <div>
            <span v-if="board.answer" class="badge bg-success">답변완료</span>
            <span v-else class="badge bg-warning text-dark">미답변</span>
            {{ board.title }}
          </div>
        </div>
        <div v-else class="col-md-8 text-start">
          <div>
            {{ board.title }}
          </div>
        </div>
        <div class="col-md-3 text-end">
          {{ formatDate(board.createdAt) }}
          {{ board.writer }}
        </div>
      </div>
      <div class="row justify-content-end mt-2">
        <div class="col-md-4 text-end">
          조회수: {{ board.views }}
        </div>
      </div>
      <image-container v-if="showImage"
                       :file-list="fileList"></image-container>
      <pre>
        <div class="content border border-2 rounded mt-4 p-4 text-start">{{
            board.content
          }}</div>
      </pre>
      <div class="files text-start" v-if="showFiles">
        <div v-for="file in fileList" :key="file.fileId">
          <a @click="downloadFile(file.fileId)">
            <span><i class="fas fa-paperclip"></i> {{
                file.originalName
              }}</span>
          </a>
        </div>
      </div>
      <div class="d-flex justify-content-start font-weight-bold bg-light p-2"
           v-if="showAnswer">
        <template v-if="board.answer">
          <div class="row text-start justify-content-start">
            <div class="col-md-12">
              {{ board.answerer }} {{ formatDate(board.answeredAt) }}
            </div>
            <pre>
              <div class="text-start content">{{ board.answer }}</div>
            </pre>
          </div>
        </template>
        <template v-else>
          <span class="text-center">
            아직 등록된 답변이 없습니다.
          </span>
        </template>
      </div>
      <div class="buttons mt-4">
        <button @click="moveToList" class="btn btn-secondary">목록</button>
        <button v-if="isAuthorized" @click="moveToModifyForm"
                class="btn btn-primary ms-2">수정
        </button>
        <button v-if="isAuthorized" @click="confirmDelete"
                class="btn btn-danger ms-2">삭제
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import {downloadFile} from "@/api/freeBoardService";
import ImageContainer from "@/components/board/ImageContainer";
import {formatDate} from "@/utils/dateUtil";

export default {
  name: "BoardDetail",
  components: {ImageContainer},
  emits: ['deleteBoard'],
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
    showCategory: {
      type: Boolean,
      default: true,
      required: false,
      description: '카테고리를 보여줄 필요 여부'
    },
    showAnswerStatus: {
      type: Boolean,
      default: false,
      required: false,
      description: '답변 상태를 보여줄 필요의 여부'
    },
    showImage: {
      type: Boolean,
      default: false,
      required: false,
      description: '이미지를 보여줄 필요의 여부'
    },
    showAnswer: {
      type: Boolean,
      default: false,
      required: false,
      description: '답변을 보여줄 필요의 여부'
    },
    showFiles: {
      type: Boolean,
      default: false,
      required: false,
      description: '파일 다운로드를 보여줄 여부'
    },
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
      const pathArray = this.$route.path.split('/');

      const typeIndex = pathArray.indexOf('boards') + 1;

      if (typeIndex > 0 && typeIndex < pathArray.length) {
        const type = pathArray[typeIndex];

        const path = `/boards/${type}`;

        this.$router.push({
          path: `${path}`,
          query: this.$route.query
        });
      }
    },
    /**
     * 수정폼으로 이동하는 메서드
     */
    moveToModifyForm() {
      const boardId = this.board.boardId;

      const pathArray = this.$route.path.split('/');

      const typeIndex = pathArray.indexOf('boards') + 1;

      if (typeIndex > 0 && typeIndex < pathArray.length) {
        const type = pathArray[typeIndex];

        const path = `/boards/${type}`;

        this.$router.push({
          path: `${path}/modify/${boardId}`,
          query: this.$route.query
        });
      }
    },
    /**
     * 삭제 여부를 물어보는 메서드
     */
    confirmDelete() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.$emit("deleteBoard");
      }
    },
    /**
     * 날짜를 포맷에 맞게 수정하는 메서드
     * @param date
     * @returns {string}
     */
    formatDate(date) {
      return formatDate(date);
    }
  }
}
</script>

<style scoped>
.content {
  max-width: 100%;
  white-space: pre-line;
}
</style>
