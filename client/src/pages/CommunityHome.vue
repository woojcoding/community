<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-6">
        <!-- 공지사항 -->
        <article class="mb-4">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="m-0 text-center flex-grow-1">공지사항</h3>
            <div class="text-right">
              <a @click="goToBoardList('notice')" class="text-decoration-none">
                <span class="mr-2">더보기</span>
                <i class="fas fa-plus"></i>
              </a>
            </div>
          </div>
          <div class="table-responsive">
            <table class="table">
              <thead>
              <tr>
                <th scope="col">번호</th>
                <th scope="col">분류</th>
                <th scope="col">제목</th>
              </tr>
              </thead>
              <tbody>
              <tr v-if="notificationList.length > 0">
                <td>{{ notificationList[0].boardId }}</td>
                <td>알림</td>
                <td>
                  <a @click="boardDetail(notificationList[0].boardId, 'notice')">
                    {{ notificationList[0].title }}
                  </a>
                </td>
              </tr>
              <tr v-for="board in noticeBoardList" :key="board.boardId">
                <td>{{ board.boardId }}</td>
                <td>{{ board.categoryName }}</td>
                <td>
                  <a @click="boardDetail(board.boardId, 'notice')">
                    {{ board.title }}
                    <span v-if="isNew(board.createdAt)"
                          class="new-label">new</span>
                  </a>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </article>
        <!-- 갤러리 -->
        <article class="mb-4">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="m-0 text-center flex-grow-1">갤러리</h3>
            <div class="text-right">
              <a @click="goToBoardList('gallery')" class="text-decoration-none">
                <span class="mr-2">더보기</span>
                <i class="fas fa-plus"></i>
              </a>
            </div>
          </div>
          <table class="table">
            <thead>
            <tr>
              <th scope="col">번호</th>
              <th scope="col">분류</th>
              <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="board in galleryBoardList" :key="board.boardId"
                class="double-height">
              <td class="align-middle">{{ board.boardId }}</td>
              <td class="align-middle">{{ board.categoryName }}</td>
              <td class="align-middle">
                <a @click="boardDetail(board.boardId, 'gallery')">
                  <img v-if="board.thumbnailUrl" :src="board.thumbnailUrl"
                       alt="Image">
                  +{{ board.imageCount }}
                  <span v-if="isNew(board.createdAt)"
                        class="new-label">new</span>
                </a>
              </td>
            </tr>
            </tbody>
          </table>
        </article>
      </div>
      <div class="col-md-6">
        <!-- 자유 게시판 -->
        <article class="mb-4">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="m-0 text-center flex-grow-1">자유 게시판</h3>
            <div class="text-right">
              <a @click="goToBoardList('free')" class="text-decoration-none">
                <span class="mr-2">더보기</span>
                <i class="fas fa-plus"></i>
              </a>
            </div>
          </div>
          <table class="table">
            <thead>
            <tr>
              <th scope="col">번호</th>
              <th scope="col">분류</th>
              <th scope="col">제목</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="board in freeBoardList" :key="board.boardId">
              <td>{{ board.boardId }}</td>
              <td>{{ board.categoryName }}</td>
              <td>
                <a @click="boardDetail(board.boardId, 'free')">
                  {{ board.title }}
                  <span>
                ({{ board.commentCount }})
                <span v-if="board.isAttached">
                  <i class="fas fa-paperclip"></i>
                </span>
              </span>
                  <span v-if="isNew(board.createdAt)"
                        class="new-label">new</span>
                </a>
              </td>
            </tr>
            </tbody>
          </table>
        </article>
        <!-- 문의 게시판 -->
        <article class="mb-4">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="m-0 text-center flex-grow-1">문의 게시판</h3>
            <div class="text-right">
              <a @click="goToBoardList('help')" class="text-decoration-none">
                <span class="mr-2">더보기</span>
                <i class="fas fa-plus"></i>
              </a>
            </div>
          </div>
          <table class="table">
            <thead>
            <tr>
              <th scope="col">번호</th>
              <th scope="col">제목</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="board in helpBoardList" :key="board.boardId">
              <td>{{ board.boardId }}</td>
              <td>
                <a @click="boardDetail(board.boardId, 'help')">
                  {{ board.title }}
                  <span v-if="board.answer">(답변완료)</span>
                  <span v-if="!board.answer">(미답변)</span>
                  <span v-if="board.secretFlag">
                <i class="fas fa-lock"></i>
              </span>
                  <span v-if="isNew(board.createdAt)"
                        class="new-label">new</span>
                </a>
              </td>
            </tr>
            </tbody>
          </table>
        </article>
      </div>
    </div>
  </div>
</template>

<script>
import {isNew} from "@/utils/dateUtil";
import {loadNoticeBoardList} from "@/api/noticeBoardService";
import {loadFreeBoardList} from "@/api/freeBoardService";
import {loadGalleryBoardList} from "@/api/galleryBoardService";
import {loadHelpBoardList} from "@/api/helpBoardService";

export default {
  name: "CommunityHome",
  data() {
    return {
      noticeBoardList: {},
      notificationList: {},
      freeBoardList: {},
      galleryBoardList: {},
      helpBoardList: {},
      boardSearch: {
        pageSize: 5,
      }
    }
  },
  created() {
    this.loadNoticeBoardList();
    this.loadFreeBoardList();
    this.loadGalleryBoardList();
    this.loadHelpBoardList();
  },
  methods: {
    /**
     * 공지 게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadNoticeBoardList() {
      try {
        this.boardSearch.pageSize = 5;

        const response = await loadNoticeBoardList(this.boardSearch);
        this.noticeBoardList = response.data.boardList;
        this.notificationList = response.data.notificationList;

      } catch (error) {
        alert(error);
      }
    },
    /**
     * 자유게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadFreeBoardList() {
      try {
        this.boardSearch.pageSize = 6;

        const response = await loadFreeBoardList(this.boardSearch);
        this.freeBoardList = response.data.boardList;
      } catch (error) {
        alert(error);
      }
    },
    /**
     * 자유게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadGalleryBoardList() {
      try {
        this.boardSearch.pageSize = 3;

        const response = await loadGalleryBoardList(this.boardSearch);

        this.galleryBoardList = response.data.boardList;
      } catch (error) {
        alert(error);
      }
    },
    /**
     * 문의 게시글 목록 데이터들을 불러오는 메서드
     *
     * @returns {Promise<void>}
     */
    async loadHelpBoardList() {
      try {
        this.boardSearch.pageSize = 6;

        const response = await loadHelpBoardList(this.boardSearch);
        this.helpBoardList = response.data.boardList;
      } catch (error) {
        alert(error);
      }
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
     * 게시글 상세보기 페이지로 이동하는 메서드
     *
     * @param boardId
     */
    boardDetail(boardId, type) {
      this.$router.push({
        path: `/boards/${type}/${boardId}`,
        query: this.$route.query
      });
    },
    goToBoardList(type) {
      this.$router.push({
        path: `/boards/${type}`,
      });
    }
  }
}
</script>

<style scoped>
.new-label {
  font-weight: bold;
  color: red;
  margin-left: 5px;
}

article {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 15px;
}

.double-height {
  height: 80.2px
}

</style>
