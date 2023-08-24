<template>
  <div v-for="board in boardList" :key="board.boardId">
    <img v-if="board.thumbnailUrl" :src="board.thumbnailUrl" alt="Image">
    <a @click="boardDetail(board.boardId)">
      {{ board.title }}
    </a>
    <div class="content">{{ board.content }}</div>
  </div>
</template>

<script>
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
};
</script>

<style scoped>

</style>
