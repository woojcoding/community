import axios from "axios";

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})


/**
 * 공지 게시글 가져오기 위해 axios 요청
 *
 * @returns {Promise<boolean|*>}
 */
export function loadNoticeBoardList(boardSearch) {
    return instance.get("/api/v1/boards/notice", {
        params: boardSearch
    })
        .then((response) => {
            console.log(response.data)
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}

/**
 * 공지 게시글 상세보기를 가져오기 위해 axios 요청
 *
 * @param boardId
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function loadNoticeBoard(boardId) {
    return instance.get(`/api/v1/boards/notice/${boardId}`)
        .then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}
