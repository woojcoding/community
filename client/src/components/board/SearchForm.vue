<template>
  <div>
    <form @submit.prevent="search">
      <p>등록일시</p>
      <input type="date" v-model="boardSearch.startDate">
      <input type="date" v-model="boardSearch.endDate">
      <select v-if="type !== 'help'" v-model="boardSearch.category">
        <option value="all">전체 카테고리</option>
        <option v-for="category in categoryList" :key="category.categoryId" :value="category.categoryId">
          {{ category.name }}
        </option>
      </select>
      <input type="text" v-model="boardSearch.keyword"
             :placeholder="type === 'notice' ? '제목 or 내용' :
       (type === 'help' ? '제목 or 내용 or 등록자' : '제목 or 내용 or 작성자')">
      <button type="submit">검색</button>
    </form>
  </div>
</template>

<script>
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
    boardSearchCondition: {
      type: Object,
      default: undefined,
      required: false,
      description: '검색 조건'
    }
  },
  emits: ['searchBoard']
  ,
  created() {
    if (this.boardSearchCondition) {
      this.boardSearch = { ...this.boardSearchCondition };
    }
  },
  data() {
    return {
      boardSearch: {},
    };
  },
  methods: {
    search() {
      this.$emit('searchBoard', this.boardSearch);
    }
  }
}
</script>

<style scoped>

</style>
