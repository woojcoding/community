<template>
  <!--<input type="file" name="files" style="display: none;"> -->
  <table id="fileInputTable">
    <tbody>
    <tr>
      <td v-if="type === 'free'">jpg, gif, png, zip 파일만 파일사이즈 2MB까지 업로드
        가능합니다.
      </td>
      <td v-if="type === 'gallery'">jpg, gif, png 파일만 파일사이즈 2MB까지 업로드
        가능합니다.
      </td>
    </tr>
    <tr v-for="(file, index) in uploadedFiles" :key="file.fileId">
      <td class="align-middle img-cell">
        <img v-if="file.thumbnailName"
             :src="`${dynamicDomain}/api/v1/files/images/${file.thumbnailName}`"
        >
        <a :id="'downloadTag_' + file.fileId"
           class="text-decoration-none"
           @click="downloadFile(file.fileId)">
          <span>{{ file.originalName }}</span>
        </a>
      </td>
      <td>
        <button type="button" class="btn btn-sm btn-danger"
                @click="removeFile(index, file.fileId)">X
        </button>
      </td>
    </tr>
    <tr v-for="(file, index) in inputFiles" :key="'input_' + index">
      <td class="align-middle img-cell">
        <img v-if="type === 'gallery' && thumbnails[index]"
             :src="thumbnails[index]" alt="Image">
        <input
            v-if="type === 'help'"
            type="file"
            @change="handleFileChange($event, index)"
            class="form-control-file"
            accept=".jpg, .jpeg, .gif, .png, application/zip"
        >
        <input
            v-else
            type="file"
            @change="handleFileChange($event, index); showThumbnail($event)"
            class="form-control-file"
            accept=".jpg, .jpeg, .gif, .png"
        >
      </td>
      <td>
        <button type="button" class="btn btn-sm btn-danger"
                @click="removeInputFile(index)">X
        </button>
      </td>
    </tr>
    </tbody>
  </table>
  <div class="d-flex justify-content-start mt-3">
    <button type="button" class="btn btn-primary me-2" @click="addFileInput">추가
    </button>
  </div>
</template>

<script>
import {downloadFile} from "@/api/freeBoardService";
import {ref} from "vue";

export default {
  name: "FileInput",
  props: {
    type: {
      type: String,
      default: undefined,
      required: true,
      description: '게시글 타입'
    },
    fileListData: {
      type: Array,
      default: undefined,
      required: false,
      description: '파일 리스트'
    },
  },
  created() {
    this.uploadedFiles = this.fileListData;
  },
  emits: ['files-updated', 'files-deleted'],
  data() {
    return {
      inputFiles: [],
      thumbnails: [],
      uploadedFiles: [],
      deleteFileIds: [],
      MAX_FILES: this.type === 'gallery' ? 20 : 5,
      fileCount: this.uploadedFiles ? this.uploadedFiles.length : 0
    };
  },
  setup() {
    const dynamicDomain = ref(process.env.VUE_APP_API_URL);

    return {
      dynamicDomain,
    };
  },
  methods: {
    /**
     * <input type="file">를 동적으로 생성하며
     * 데이터 변동 사항을 부모 컴포넌트로 emit할 수 있도록 이벤트 리스너 추가
     */
    addFileInput() {
      if (this.fileCount + this.inputFiles.length >= this.MAX_FILES) {
        alert('최대 ' + this.MAX_FILES + '개까지만 첨부 가능합니다.');

        return;
      }

      this.inputFiles.push({});
    },
    /**
     * 파일의 변동 사항을 부모 컴포넌트로  emit 하는 메서드
     * @param event
     * @param index
     */
    handleFileChange(event, index) {
      const selectedFile = event.target.files[0];

      if (selectedFile) {
        this.inputFiles[index] = selectedFile;

        this.$emit('files-updated', this.inputFiles);
      }
    },
    /**
     * 파일 변동 사항을 부모 컴포넌트로  emit 하는 메서드
     *
     * @param index
     */
    removeInputFile(index) {
      // 입력란 제거
      this.inputFiles.splice(index, 1);

      this.$emit('files-updated', this.inputFiles);
    },
    /**
     * 저장된 파일을 삭제하기 위한 메서드
     *
     * @param fileId
     */
    removeFile(index, fileId) {
      this.uploadedFiles.splice(index, 1);

      this.deleteFileIds.push(fileId);

      this.$emit('files-deleted', this.deleteFileIds);
    },
    /**
     * 썸네일을 보여주기 위한 메서드
     *
     * @param event
     */
    showThumbnail(event) {
      const file = event.target.files[0];

      const imageTypes = ['image/jpeg', 'image/gif', 'image/png'];

      if (file && imageTypes.includes(file.type)) {
        const reader = new FileReader();
        reader.readAsDataURL(file);

        reader.onload = () => {
          this.thumbnails.push(reader.result);
        }
      }
    },
    /**
     * 파일을 다운르도하는 메서드
     *
     * @param fileId
     * @returns {Promise<void>}
     */
    async downloadFile(fileId) {
      try {
        await downloadFile(fileId);
      } catch (error) {
        alert(error);
      }
    },
  }
}
</script>

<style scoped>
.img-cell {
  text-align: left;
}
img {
  height: 60px;
  width: 60px;
}
</style>
