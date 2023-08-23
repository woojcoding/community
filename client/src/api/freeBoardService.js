import axios from "axios";
import store from "@/store";

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})

/**
 *  getMapping을 제외한 axios요청 헤더에 토큰 부여
 */
instance.interceptors.request.use(
    (config) => {
        if (config.method !== "get") {
            const accessToken = store.state.auth.token;

            if (accessToken) {
                config.headers.Authorization = accessToken;
            }
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

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
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}

/**
 * 자유게시글 수정을 위한 데이터를 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function loadFreeBoardForModify(boardId) {
    return instance.get(`/api/v1/boards/free/modify/${boardId}`, {
        headers: {
            Authorization: store.state.auth.token
        }
    }).then((response) => {
        return response.data;
    }).catch((error) => {
        throw error.response.data.message
    });
}

/**
 * 자유 게시글을 작성하기 위해 axios 요청
 *
 * @param formData
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function postFreeBoard(formData) {
    return instance.post("/api/v1/boards/free", formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then((response) => {
        return response.data;
    }).catch((error) => {
        throw error.response.data.message;
    });
}

/**
 * 자유 게시글을 수정하기 위해 axios 요청
 *
 * @param formData
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function patchFreeBoard(formData, boardId) {
    return instance.patch(`/api/v1/boards/free/${boardId}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then((response) => {
        return response.data;
    }).catch((error) => {
        throw error.response.data.message;
    });
}

/**
 * 자유 게시글을 삭제하기 위해 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function deleteFreeBoard(boardId) {
    return instance.delete(`/api/v1/boards/free/${boardId}`
    ).then((response) => {
        return response.data;
    }).catch((error) => {
        throw error.response.data.message;
    });
}

/**
 * 파일 다운로드를 axios 요청 한 후 링크를 생성하여 body에 붙여주는 메서드
 *
 * @param fileId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
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

/**
 * 댓글을 작성하기 위해 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function postComment(boardId, comment) {
    return instance.post(`/api/v1/boards/free/${boardId}/comment`, comment, {
        headers: {
            "Content-Type": "application/json"
        },
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            throw error.response.data.message;
        });
}
