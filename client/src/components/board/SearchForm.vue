<template>
  <div class="border p-4">
    <form @submit.prevent="search"
          class="row g-3 align-items-center">
      <div class="col-md-1 text-start">
        <p class="m-0">일자</p>
      </div>
      <div class="col-md-2">
        <input type="date" v-model="boardSearch.startDate" class="form-control">
      </div>
      <div class="col-md-1">
        <p class="m-0">~</p>
      </div>
      <div class="col-md-2">
        <input type="date" v-model="boardSearch.endDate" class="form-control">
      </div>
      <div class="col-md-2 d-flex justify-content-center"
           v-if="type !== 'help'">
        <select v-model="boardSearch.category"
                class="form-select">
          <option value="all">전체 카테고리</option>
          <option v-for="category in categoryList" :key="category.categoryId"
                  :value="category.categoryId">
            {{ category.name }}
          </option>
        </select>
      </div>
      <div class="col-md-2 d-flex justify-content-center" v-else></div>
      <div class="col-md-3">
        <input id="keyword" type="text" v-model="boardSearch.keyword"
               class="form-control"
               :placeholder="type === 'notice' ? '제목 or 내용' : (type === 'help' ? '제목 or 내용 or 등록자' : '제목 or 내용 or 작성자')">
      </div>
      <div class="col-md-1 text-end">
        <button type="submit" class="btn btn-primary">검색</button>
      </div>
      <div v-if="type === 'help'" class="col-md-12 text-start">
        <input id="my-post-only" type="checkbox"
               v-model="boardSearch.displayMyPostsOnly">
        <span class="ml-1"> 나의 문의내역만 보기</span>
      </div>
    </form>
  </div>
  <div class="row mt-4 p-4">
    <div class="col-md-4 text-start">
      <select id="page-size" class="form-control-sm"
              v-model="boardSearch.pageSize" @change="search">
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="30">30</option>
        <option value="40">40</option>
        <option value="50">50</option>
      </select>
      <span> 개씩 보기</span>
    </div>
    <div class="col-md-8 text-end">
      <span>정렬 </span>
      <select id="sort-by" class="form-control-sm" v-model="boardSearch.sortBy"
              @change="search">
        <option value="createdAt">등록일시</option>
        <option v-if="type !== 'help'" value="category">분류</option>
        <option value="title">제목</option>
        <option value="views">조회수</option>
      </select>
      <select id="sort" class="form-control-sm" v-model="boardSearch.sort"
              @change="search">
        <option value="desc">내림차순</option>
        <option value="asc">오름차순</option>
      </select>
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
