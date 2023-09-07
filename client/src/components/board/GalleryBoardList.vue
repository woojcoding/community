<template>
  <div class="gallery-list">
    <div v-for="board in boardList" :key="board.boardId"
         class="gallery-item">
      <a @click="boardDetail(board.boardId)"
         class="text-decoration-none text-dark">
        <div class="row">
          <div class="col-md-2 align-self-start">
            <a @click="boardDetail(board.boardId)" class="text-decoration-none">
              <div class="align-items-start">
                <img v-if="board.thumbnailName"
                     :src="`${dynamicDomain}/api/v1/files/images/${board.thumbnailName}`"
                     alt="Image" class="mr-2">
              </div>
            </a>
          </div>
          <div class="col-md-10">
            <div class="row">
              <div class="col-md-12 text-start">
                {{ board.title }}
                <span v-if="board.imageCount > 1">
                  +{{ board.imageCount - 1 }}</span>
                <span v-if="isNew(board.createdAt)"
                      class="new-label ml-2">new</span>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12 text-start content">
                {{ board.content }}
              </div>
            </div>
          </div>
        </div>
      </a>
    </div>
  </div>
</template>


<script>
import {isNew} from "@/utils/dateUtil";
import {ref} from "vue";

export default {
  name: "GalleryBoardList",
  props: {
    boardList: {
      type: Array,
      default: undefined,
      required: true,
      description: '게시글 리스트'
    },
  },
  setup() {
    const dynamicDomain = ref(process.env.VUE_APP_API_URL);

    return {
      dynamicDomain,
    };
  },
  methods: {
    /**
     * 게시글 상세보기 페이지로 이동하는 메서드
     *
     * @param boardId
     */
    boardDetail(boardId) {
      this.$router.push({
        path: `/boards/gallery/${boardId}`,
        query: this.$route.query
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

.gallery-list {
  display: flex;
  flex-direction: column; /* 수직 정렬 설정 */
  align-items: center;
}

.gallery-item {
  border: 1px solid #e5e5e5;
  box-sizing: border-box;
  padding: 10px;
  margin: 10px 0;
  width: 100%;
}
</style>
