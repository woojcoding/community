<template>
  <form id="writeForm" @submit.prevent="submitForm" class="mt-4">
    <div class="table-responsive">
      <table class="table border">
        <!-- 카테고리 (문의게시글 미사용) -->
        <tr v-if="type !== 'help'" class="border-bottom">
          <td class="align-middle">분류</td>
          <td>
            <select v-model="board.categoryId" name="categoryId"
                    class="form-select">
              <option value="all" selected disabled>카테고리 선택</option>
              <option v-for="category in categoryListData"
                      :key="category.categoryId" :value="category.categoryId">
                {{ category.name }}
              </option>
            </select>
          </td>
        </tr>
        <!-- 제목 -->
        <tr>
          <td class="align-middle border-bottom" >제목</td>
          <td>
            <textarea rows="1" cols="100" name="title" id="title"
                      style="resize: none; overflow: hidden"
                      v-model="board.title" class="form-control"></textarea>
          </td>
        </tr>
        <!-- 내용 (문의 게시판의 경우 질문) -->
        <tr>
          <td class="align-middle border-bottom">{{ type === 'help' ? '질문' : '내용' }}</td>
          <td>
            <textarea rows="20" cols="100" name="content" id="content"
                      style="resize: none"
                      v-model="board.content" class="form-control"></textarea>
          </td>
        </tr>
        <!-- 문의글에서만 사용되는 비밀글 체크 -->
        <tr v-if="type === 'help'">
          <td class="align-middle border-bottom">비밀글</td>
          <td><input type="checkbox" v-model="board.secretFlag"
                     class="form-check-input"></td>
        </tr>
        <!-- 갤러리와 자유게시판에서 사용되는 파일 -->
        <tr v-if="type === 'gallery' || type === 'free'">
          <td class="align-middle border-bottom">첨부</td>
          <td>
            <file-input
                :type=type
                :file-list-data="fileListData"
                @files-updated="updateFiles"
                @files-deleted="deleteFiles"></file-input>
          </td>
        </tr>
      </table>
    </div>
    <div class="d-flex justify-content-center mt-3">
      <button type="submit" class="btn btn-primary me-2">등록</button>
      <button @click="cancel" class="btn btn-secondary">취소</button>
    </div>
  </form>
</template>

<script>
import FileInput from "@/components/file/FileInput";

export default {
  name: "BoardWriteForm",
  components: {FileInput},
  props: {
    boardData: {
      type: Object,
      default: undefined,
      required: false,
      description: '게시글'
    },
    fileListData: {
      type: Array,
      default: undefined,
      required: false,
      description: '서버에 저장된 파일 리스트'
    },
    categoryListData: {
      type: Object,
      default: undefined,
      required: true,
      description: '카테고리 리스트'
    },
    type: {
      type: String,
      default: undefined,
      required: true,
      description: '게시글 타입'
    },
  },
  emits: ['save'],
  data() {
    return {
      board: {
        categoryId: 'all',
        title: '',
        content: ''
      },
      files: [],
      deleteFileIds: [],
    }
  },
  created() {
    if (this.boardData) {
      this.board = {...this.boardData};
    }
  },
  methods: {
    /**
     * 유효성 검증을 하는 메서드
     *
     * @returns {boolean}
     */
    validateForm() {
      const errorMessages = [];

      if (this.board.categoryId === 'all' && this.type !== 'help') {
        errorMessages.push('카테고리를 선택해주세요.');
      }
      if (!this.board.title) {
        errorMessages.push('제목은 필수 입력 사항입니다.');
      } else if (this.board.title.length > 100) {
        errorMessages.push('제목은 100자를 초과할 수 없습니다.');
      }
      if (!this.board.content) {
        errorMessages.push('내용은 필수 입력 사항입니다.');
      } else if (this.board.content.length > 4000) {
        errorMessages.push('내용은 4000자를 초과할 수 없습니다.');
      }

      if (errorMessages.length > 0) {
        alert(errorMessages.join('\n'));
        return false;
      }

      return true;
    },
    /**
     * 폼 데이터를 부모 컴포넌트로 emit하는 메서드
     */
    submitForm() {
      // 클라이언트 측 유효성 검증
      if (!this.validateForm()) {
        return; // 유효성 검증 실패 시 전송하지 않음
      }
      this.$emit('save', this.board, this.files, this.deleteFileIds);
    },
    /**
     * 취소를 눌러 이전 페이지로 가는 메서드
     */
    cancel() {
      const boardId = this.$route.params.boardId;

      let path = `/boards/${this.type}`;

      if (boardId) {
        path += `/${boardId}`;
      }

      this.$router.push({
        path: path,
        query: {
          ...this.$route.query
        }
      });
    },
    /**
     * 자식 컴포넌트로 부터 file을 받아 데이터를 반영하는 메서드
     *
     * @param newFiles
     */
    updateFiles(newFiles) {
      this.files = newFiles
    },
    /**
     * 자식 컴포넌트로 부터 삭제할 fileId를 받아 데이터를 반영하는 메서드
     *
     * @param deleteFileIds
     */
    deleteFiles(deleteFileIds) {
      this.deleteFileIds = deleteFileIds;
    }
  }
}
</script>

<style scoped>

</style>
