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
 * 갤러리 게시글 가져오기 위해 axios 요청
 *
 * @returns {Promise<boolean|*>}
 */
export function loadGalleryBoardList(boardSearch) {
    return instance.get("/api/v1/boards/gallery", {
        params: boardSearch
    })
        .then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}

/**
 * 갤러리 게시글 상세보기를 가져오기 위해 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function loadGalleryBoard(boardId) {
    return instance.get(`/api/v1/boards/gallery/${boardId}`)
        .then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}

/**
 * 갤러리 게시글 수정을 위한 데이터를 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function loadGalleryBoardForModify(boardId) {
    return instance.get(`/api/v1/boards/gallery/modify/${boardId}`, {
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
 * 갤러리 게시글을 작성하기 위해 axios 요청
 *
 * @param formData
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function postGalleryBoard(formData) {
    return instance.post("/api/v1/boards/gallery", formData, {
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
 * 갤러리 게시글을 수정하기 위해 axios 요청
 *
 * @param formData
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function patchGalleryBoard(formData, boardId) {
    return instance.patch(`/api/v1/boards/gallery/${boardId}`, formData, {
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
 * 갤러리 게시글을 삭제하기 위해 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function deleteGalleryBoard(boardId) {
    return instance.delete(`/api/v1/boards/gallery/${boardId}`
    ).then((response) => {
        return response.data;
    }).catch((error) => {
        throw error.response.data.message;
    });
}

/**
 * 갤러리 게시글의 썸네일을 조회하기 위해 axios 요청
 *
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function loadThumbnail(boardId) {
    return instance.get(`/api/v1/boards/gallery/${boardId}/thumbnail`)
        .then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}
