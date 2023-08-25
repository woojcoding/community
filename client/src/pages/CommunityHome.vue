<template>
  <h2>홈화면</h2>
  <article>
    <h3> 공지사항 </h3>
    <a @click="goToBoardList('notice')">
      더보기+
    </a>
    <table>
      <tr>
        <td>번호</td>
        <td>분류</td>
        <td>제목</td>
      </tr>
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
            <span v-if="isNew(board.createdAt)" class="new-label">new</span>
          </a>
        </td>
      </tr>
    </table>
  </article>
  <article>
    <h3> 자유게시판 </h3>
    <a @click="goToBoardList('free')">
      더보기+
    </a>
    <table>
      <tr>
        <td>번호</td>
        <td>분류</td>
        <td>제목</td>
      </tr>
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
            <span v-if="isNew(board.createdAt)" class="new-label">new</span>
          </a>
        </td>
      </tr>
    </table>
  </article>
  <article>
    <h3> 갤러리 </h3>
    <a @click="goToBoardList('gallery')">
      더보기+
    </a>
    <table>
      <tr>
        <td>번호</td>
        <td>분류</td>
        <td></td>
      </tr>
      <tr v-for="board in galleryBoardList" :key="board.boardId">
        <td>{{ board.boardId }}</td>
        <td>{{ board.categoryName }}</td>
        <td>
          <a @click="boardDetail(board.boardId,'gallery')">
            <img v-if="board.thumbnailUrl" :src="board.thumbnailUrl" alt="Image">
             +{{ board.imageCount }}
            <span v-if="isNew(board.createdAt)" class="new-label">new</span>
          </a>
        </td>
      </tr>
    </table>
  </article>
  <article>
    <h3> 문의 게시판 </h3>
    <a @click="goToBoardList('help')">
      더보기+
    </a>
    <table>
      <tr>
        <td>번호</td>
        <td>제목</td>
      </tr>
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
            <span v-if="isNew(board.createdAt)" class="new-label">new</span>
          </a>
        </td>
      </tr>
    </table>
  </article>
</template>

<script>
import {isNew} from "@/utils/dateUtil";
import {loadNoticeBoardList} from "@/api/noticeBoardService";
import {loadFreeBoardList} from "@/api/freeBoardService";
import {loadGalleryBoardList, loadThumbnail} from "@/api/galleryBoardService";
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
        for (const board of this.galleryBoardList) {
          await this.loadThumbnailUrl(board);
        }
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
     * 썸네일을 불러와 board에 지정해주는 메서드
     *
     * @param board
     * @returns {Promise<void>}
     */
    async loadThumbnailUrl(board) {
      try {
        const response = await loadThumbnail(board.boardId);

        board.thumbnailUrl = response.data.url;
      } catch (error) {
        console.log(error);
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
</style>
