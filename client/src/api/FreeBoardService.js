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


