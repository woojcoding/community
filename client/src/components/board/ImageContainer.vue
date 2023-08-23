<template>
  <div>
      <div class="carousel-inner">
        <div v-for="file in fileList" :key="file.fileId">
          <img :src="file.imageUrl" alt="이미지 없음">
        </div>
      </div>
  </div>
</template>
<script>


export default {
  name: "ImageContainer",
  props: {
    fileList: {
      type: Array,
      default: undefined,
      required: false,
      description: "파일 리스트",
    },
  },
};
</script>

<style scoped>

</style>
