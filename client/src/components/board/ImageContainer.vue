<template>
  <div class="bg-dark">
    <div id="carousel" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div v-for="(file, index) in fileList" :key="file.fileId"
             class="carousel-item" :class="{ 'active': index === 0 }">
          <div class="img-container">
            <img :src="`${dynamicDomain}/api/v1/files/images/${file.savedName}`"
                 alt="이미지 없음" class="img-fluid">
          </div>
        </div>
      </div>
      <button v-if="fileList.length > 1" class="carousel-control-prev"
              type="button" data-bs-target="#carousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      </button>
      <button v-if="fileList.length > 1" class="carousel-control-next"
              type="button" data-bs-target="#carousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
      </button>
    </div>
  </div>
</template>

<script>
import {ref} from "vue";

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
  setup() {
    const dynamicDomain = ref(process.env.VUE_APP_API_URL);

    return {
      dynamicDomain,
    };
  },
};
</script>

<style scoped>
.img-container {
  width: 100%;
  height: 600px;
}

img {
  object-fit: contain;
  height: 100%;
  width: 100%;
}
</style>
