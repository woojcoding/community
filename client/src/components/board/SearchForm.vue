<template>
  <div class="search-container">
    <form @submit.prevent="search"
          class="d-flex flex-wrap align-items-center">
      <div class="row justify-content-start">
        <div class="col-md-1">
          <p class="m-0">일자</p>
        </div>
        <div class="col-md-2">
          <input type="date" v-model="boardSearch.startDate">
        </div>
        <div class="col-md-1">
          <p class="m-0">~</p>
        </div>
        <div class="col-md-2">
          <input type="date" v-model="boardSearch.endDate">
        </div>
        <div class="col-md-2" v-if="type !== 'help'">
          <select v-model="boardSearch.category">
            <option value="all">전체 카테고리</option>
            <option v-for="category in categoryList" :key="category.categoryId"
                    :value="category.categoryId">
              {{ category.name }}
            </option>
          </select>
        </div>
        <div class="col-md-1" v-else></div>
        <div class="col-md-2">
          <input type="text" v-model="boardSearch.keyword"
                 :placeholder="type === 'notice' ? '제목 or 내용' : (type === 'help' ? '제목 or 내용 or 등록자' : '제목 or 내용 or 작성자')">
        </div>
        <div v-if="type==='help'" class="col-md-3">
          <button type="submit">검색</button>
        </div>
        <div v-else class="col-md-2">
          <button type="submit">검색</button>
        </div>
      </div>
      <div v-if="type === 'help'" class="col-md-12">
        <input type="checkbox" v-model="boardSearch.displayMyPostsOnly">
        <span class="ml-1">나의 문의내역만 보기</span>
      </div>
    </form>
  </div>
  <div class="sort-container">
    <div class="row mt-4 justify-content-start">
      <div class="col-md-6">
        <select v-model="boardSearch.pageSize" @change="search">
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="30">30</option>
          <option value="40">40</option>
          <option value="50">50</option>
        </select>
      </div>
      <div class="col-md-6">
        <p>개씩</p>
      </div>
    </div>
    <div class="row mt-4 justify-content-end">
      <div class="col-md-4">
        <p>정렬</p>
      </div>
      <div class="col-md-4">
        <select v-model="boardSearch.sortBy" @change="search">
          <option value="createdAt">등록일시</option>
          <option v-if="type !== 'help'" value="category">분류</option>
          <option value="title">제목</option>
          <option value="views">조회수</option>
        </select>
      </div>
      <div class="col-md-2">
        <select v-model="boardSearch.sort" @change="search">
          <option value="desc">내림차순</option>
          <option value="asc">오름차순</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
import dayjs from "dayjs";

export default {
  name: "SearchForm",
  props: {
    categoryList: {
      type: Object,
      default: undefined,
      required: false,
      description: '카테고리 리스트'
    },
    type: {
      type: String,
      default: undefined,
      required: true,
      description: '게시글 타입'
    },
  },
  emits: ['searchBoard']
  ,
  data() {
    return {
      boardSearch: {
        startDate: dayjs().subtract(30, 'day').format('YYYY-MM-DD'),
        endDate: dayjs().format('YYYY-MM-DD'),
        category: 'all',
        keyword: '',
        pageNum: 1,
        pageSize: 10,
        sortBy: 'createdAt',
        sort: 'desc',
      },
    };
  },
  methods: {
    /**
     * 검색을 하기 위해 검색조건을 emit
     */
    search() {
      this.$emit('searchBoard', this.boardSearch);
    },
  }
}
</script>

<style scoped>
.search-container {
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 15px;
}

.sort-container {
  display: flex;
  justify-content: space-evenly;
}
</style>
