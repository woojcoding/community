<template>
  <div class="bg-dark">
    <div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div v-for="(file, index) in fileList" :key="file.fileId"
             class="carousel-item" :class="{ 'active': index === 0 }">
          <img :src="file.imageUrl" alt="이미지 없음" class="img-fluid">
        </div>
      </div>
      <button v-if="fileList.length > 1" class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      </button>
      <button v-if="fileList.length > 1" class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
      </button>
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
