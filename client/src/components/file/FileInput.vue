<template>
  <table>
    <tr>
      <td>첨부</td>
      <td>
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
          <tr v-for="file in fileListData" :key="file.fileId">
            <td>
              <img v-if="file.thumbnailName"
                   :src="'/thumbnails/' + file.thumbnailName" alt="thumbnail">
              <a :id="'downloadTag_' + file.fileId"
                 @click="downloadFile(file.fileId)">
                <span>{{ file.originalName }}</span>
              </a>
            </td>
            <td>
              <button type="button" @click="removeFile(file.fileId)">X</button>
            </td>
          </tr>
          </tbody>
        </table>
        <button type="button" @click="addFileInput">추가</button>
      </td>
    </tr>
  </table>
</template>

<script>
import {downloadFile} from "@/api/FreeBoardService";

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
  emits: ['files-updated', 'files-deleted'],
  data() {
    return {
      files: [],
      deleteFileIds: [],
      MAX_FILES: this.type === 'gallery' ? 20 : 5,
      fileCount: this.fileListData ? this.fileListData.length : 0
    };
  },
  methods: {
    /**
     * <input type="file">를 동적으로 생성하며
     * 데이터 변동 사항을 부모 컴포넌트로 emit할 수 있도록 이벤트 리스너 추가
     */
    addFileInput() {
      if (this.fileCount >= this.MAX_FILES) {
        alert('최대' + (this.MAX_FILES) + '개까지만 첨부 가능합니다.');
        return;
      }

      const fileIndex = this.files.length;

      const fileInputTable = document.getElementById('fileInputTable');

      const newRow = document.createElement('tr');

      const inputCell = document.createElement('td');

      const inputField = document.createElement('input');
      inputField.setAttribute('type', 'file');
      inputField.setAttribute('id', 'fileInput_' + fileIndex);
      inputField.addEventListener('change', (event) => this.handleFileChange(event, fileIndex));

      if (this.type === 'gallery') {
        inputField.setAttribute('accept', '.jpg, .jpeg, .gif, .png');
        inputField.setAttribute('onchange', this.showThumbnail(this.fileCount));

        const thumbnailImg = document.createElement('img');
        thumbnailImg.setAttribute('id', 'thumbnailImg_' + this.fileCount);
        thumbnailImg.style.width = '30px';
        thumbnailImg.style.height = '30px';

        inputCell.appendChild(thumbnailImg);
      } else {
        inputField.setAttribute('accept', '.jpg, .jpeg, .gif, .png, application/zip');
      }

      // tr을 없애는 버튼 생성
      const removeButton = document.createElement('button');
      removeButton.setAttribute('type', 'button');
      removeButton.innerText = 'X';

      removeButton.addEventListener('click', () => {
        this.handleFileRemove(fileIndex);

        fileInputTable.removeChild(newRow);

        this.fileCount--;
      });

      inputCell.appendChild(inputField);
      inputCell.appendChild(removeButton);

      newRow.appendChild(inputCell);

      fileInputTable.appendChild(newRow);

      this.fileCount++;
    },

    /**
     * 파일의 변동 사항을 부모 컴포넌트로  emit 하는 메서드
     *
     * @param event
     * @param fileIndex
     */
    handleFileChange(event, fileIndex) {
      const selectedFile = event.target.files[0];
      this.files[fileIndex] = selectedFile;
      this.$emit('files-updated', this.files);
    },
    /**
     * 파일 변동 사항을 부모 컴포넌트로  emit 하는 메서드
     *
     * @param fileIndex
     */
    handleFileRemove(fileIndex) {
      this.files.splice(fileIndex, 1);
      this.$emit('files-updated', this.files);
    },
    /**
     * 저장된 파일을 삭제하기 위한 메서드
     *
     * @param fileId
     */
    removeFile(fileId) {
      // <a> 태그가 속한 tr제거
      const downloadTag = document.getElementById('downloadTag_' + fileId);

      if (downloadTag) {
        const tr = downloadTag.closest('tr');

        if (tr) {
          tr.remove();
        }
      }

      this.fileCount--;

      this.deleteFileIds.push(fileId);

      this.$emit('files-deleted', this.deleteFileIds);
    },
    /**
     * 썸네일을 보여주기 위한 메서드
     *
     * @param fileIndex
     */
    showThumbnail(fileIndex) {
      const fileInput = document.getElementById('fileInput_' + fileIndex);

      const thumbnailImg = document.getElementById('thumbnailImg_' + fileIndex);
      thumbnailImg.innerHTML = '';

      const file = fileInput.files[0];

      const imageTypes = ['image/jpeg', 'image/gif', 'image/png'];

      if (file && imageTypes.includes(file.type)) {
        const reader = new FileReader();

        reader.onload = function (e) {
          thumbnailImg.src = e.target.result;
        };

        reader.readAsDataURL(file);
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
  },
};
</script>
