import axios from "axios";


const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})

/**
 * 자유 게시글 가져오기 위해 axios 요청
 *
 * @returns {Promise<boolean|*>}
 */
export function loadFreeBoardList(boardSearch) {
    return instance.get("/api/v1/boards/free", {
        params: boardSearch
    })
        .then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}

/**
 * 자유게시글 상세보기를 가져오기 위해 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function loadFreeBoard(boardId) {
    return instance.get(`/api/v1/boards/free/${boardId}`)
        .then((response) => {
            console.log(response)
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}

export function downloadFile(fileId) {
    return instance.get(`/api/v1/files/${fileId}`, {
        responseType: 'blob'
    })
        .then((response) => {
            console.log(response.headers)
            //header content-disposition 헤더에서 파일이름 추출.
            const encodedName = response.headers['content-disposition'].split('filename=')[1]

            // 이름 디코딩
            const name = decodeURIComponent(encodedName.replace(/^"|"$/g, ''));

            // 헤더로 받아온 content-type과 Blob 파일을 url로 지정 후 a태그에 url 지정
            const url = window.URL.createObjectURL(new Blob([response.data]
                , {type: response.headers['content-type']}))

            const link = document.createElement('a')

            link.href = url
            link.setAttribute('download', name)

            document.body.appendChild(link)

            link.click()
            link.remove();
        })
        .catch((error) => {
            console.log(error);
            throw error.response.data.message
        })
}



