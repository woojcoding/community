<template>
  <div v-for="board in boardList" :key="board.boardId" class="mb-3 border p-3">
    <div class="align-items-start">
      <img v-if="board.thumbnailUrl" :src="board.thumbnailUrl" alt="Image" class="mr-2">
    </div>
    <div class="title">
      <a @click="boardDetail(board.boardId)" class="text-decoration-none">
        {{ board.title }}
        +{{ board.imageCount }}
      </a>
      <span v-if="isNew(board.createdAt)" class="new-label ml-2">new</span>
    </div>
    <div class="content">{{ board.content }}</div>
  </div>
</template>


<script>
import {isNew} from "@/utils/dateUtil";

export default {
  name: "GalleryBoardList",
  props: {
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
    type: {
      type: String,
      default: undefined,
      required: true,
      description: '게시글 타입'
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
        query: {
          ...this.boardSearch,
          boardId: boardId
        }
      });
    },
    /**
     * New 표시 여부를 결정해주는 메서드
     * @param createdAt
     * @returns {boolean}
     */
    isNew(createdAt) {
      return isNew(createdAt);
    },
  }
};
</script>

<style scoped>
.new-label {
  font-weight: bold;
  color: red;
  margin-left: 5px;
}
</style>
