<template>
  <div>
    <form @submit.prevent="search">
      <p>등록일시</p>
      <input type="date" v-model="boardSearch.startDate">
      <input type="date" v-model="boardSearch.endDate">
      <select v-if="type !== 'help'" v-model="boardSearch.category">
        <option value="all">전체 카테고리</option>
        <option v-for="category in categoryList" :key="category.categoryId"
                :value="category.categoryId">
          {{ category.name }}
        </option>
      </select>
      <input type="text" v-model="boardSearch.keyword"
             :placeholder="type === 'notice' ? '제목 or 내용' :
       (type === 'help' ? '제목 or 내용 or 등록자' : '제목 or 내용 or 작성자')">
      <button type="submit">검색</button>
    </form>
  </div>
  <div>
    <select v-model="boardSearch.pageSize" @change="search">
      <option value="10">10</option>
      <option value="20">20</option>
      <option value="30">30</option>
      <option value="40">40</option>
      <option value="50">50</option>
    </select>
    <p>개씩 보기</p>

    <p>정렬</p>
    <select v-model="boardSearch.sortBy" @change="search">
      <option value="createdAt">등록일시</option>
      <option v-if="type !== 'help'" value="category">분류</option>
      <option value="title">제목</option>
      <option value="views">조회수</option>
    </select>
    <select v-model="boardSearch.sort" @change="search">
      <option value="desc">내림차순</option>
      <option value="asc">오름차순</option>
    </select>
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

</style>
