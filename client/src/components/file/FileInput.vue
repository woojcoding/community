<template>
  <!--<input type="file" name="files" style="display: none;"> -->
  <table id="fileInputTable">
    <tbody>
    <tr>
      <td>{{ acceptFileFormat }} 파일만 파일사이즈 2MB까지 업로드
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
        <img v-if="requireThumbnail && thumbnails[index]"
             :src="thumbnails[index]" alt="Image">
        <input
            v-if="requireThumbnail"
            type="file"
            @change="handleFileChange($event, index); showThumbnail($event)"
            class="form-control-file"
            accept={{acceptFileFormat}}
        >
        <input
            v-else
            type="file"
            @change="handleFileChange($event, index)"
            class="form-control-file"
            accept={{acceptFileFormat}}
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
    fileListData: {
      type: Array,
      default: undefined,
      required: false,
      description: '파일 리스트'
    },
    maxFileCount: {
      type: Number,
      default: 0,
      required: false,
      description: '첨부 가능한 파일 수'
    },
    acceptFileFormat: {
      type: String,
      default: undefined,
      required: false,
      description: '첨부 가능한 파일 포맷'
    },
    requireThumbnail: {
      type: Boolean,
      default: false,
      required: false,
      description: '썸네일 필요 여부'
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
      if (this.fileCount + this.inputFiles.length >= this.maxFileCount) {
        alert('최대 ' + this.maxFileCount + '개까지만 첨부 가능합니다.');

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
        // 파일 크기를 확인하여 2MB 이하인지 검사
        if (selectedFile.size <= 2 * 1024 * 1024) {
          this.inputFiles[index] = selectedFile;
          this.$emit('files-updated', this.inputFiles);
        } else {
          alert('파일은 2MB 이하만 가능합니다.');
          
          event.target.value = ''; // 파일 선택 취소
        }
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
