<template>
  <form id="writeForm" @submit.prevent="submitForm">
    <table>
      <!-- 카테고리 (문의게시글 미사용) -->
      <tr v-if="type !== 'help'">
        <td>분류</td>
        <td>
          <select v-model="board.categoryId" name="categoryId">
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
        <td>제목</td>
        <td>
          <textarea rows="1" cols="100" name="title" id="title"
                    style="resize: none; overflow: hidden"
                    v-model="board.title"></textarea>
          <span v-if="type === 'help' && board.answer !== null">(답변완료)</span>
          <span v-if="type === 'help' && board.answer === null">(미답변)</span>
        </td>
      </tr>
      <!-- 내용 (문의 게시판의 경우 질문) -->
      <tr>
        <td>{{ type === 'help' ? '질문' : '내용' }}</td>
        <td>
          <textarea rows="20" cols="100" name="content" id="content"
                    style="resize: none"
                    v-model="board.content"></textarea>
        </td>
      </tr>
      <!-- 문의게시판에서만 사용되는 답변 -->
      <tr v-if="type === 'help'">
        <td>답변</td>
        <td>
          <textarea rows="20" cols="100" name="answer" id="answer"
                    style="resize: none"
                    v-model="board.answer"></textarea>
        </td>
      </tr>
      <!-- 공지글에서만 사용되는 알림글 체크 -->
      <tr v-if="type === 'notice'">
        <td>알림글</td>
        <td>
          <input type="checkbox" name="notificationFlag"
                 v-model="board.notificationFlag">
        </td>
      </tr>
    </table>
    <!-- 갤러리와 자유게시판에서 사용되는 파일 -->
    <file-input
        :type=type
        :file-list-data="fileListData"
        @files-updated="updateFiles"
        @files-deleted="deleteFiles"></file-input>
    <button type="submit">등록</button>
    <button @click="cancel">취소</button>
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
      required: false,
      description: '게시글 타입'
    },
  },
  emits: ['save'],
  data() {
    return {
      board: {
        categoryId: 'all',
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
     * 폼 데이터를 부모 컴포넌트로 emit하는 메서드
     */
    submitForm() {
      this.$emit('save', this.board, this.files, this.deleteFileIds);
    },
    /**
     * 취소를 눌러 게시글 목록으로 가는 메서드
     */
    cancel() {
      this.$router.push({
        path: `/boards/free/`,
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
