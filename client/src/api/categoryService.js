import axios from "axios";

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})

/**
 * 카테고리를 조회하기 위해 axios 요청
 *
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function loadCategoryList(type) {
    return instance.get(`/api/v1/categories/${type}`)
        .then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}
