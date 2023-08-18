import axios from "axios";


const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})

/**
 * 아이디 중복체크를 axios 요청
 *
 * @param accountId 계정Id
 * @returns {Promise<boolean|*>}
 */
export function confirmIdDuplication(accountId) {
    return instance.get("/api/v1/user/accountId", {
        params: {
            accountId: encodeURIComponent(accountId)
        }
    }).then((response) => {
        if (response.data.status === 'SUCCESS') {
            return response.data;
        } else {
            throw {
                response: response
            };
        }
    }).catch((error) => {
        throw error.response.data.message
    });
}

/**
 * 회원가입을 axios 요청
 * @param userDto 회원정보
 * @returns {Promise<boolean>}
 */
export function signUpUser(userDto) {
    return instance.post("/api/v1/user", userDto)
        .then((response) => {
            return response.data;
        }).catch((error) => {
            throw error.response.data.message
        });
}
