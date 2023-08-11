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
export async function confirmIdDuplication(accountId) {
    try {
        const response = await instance.get("/api/v1/user/accountId", {
            params: {
                accountId: encodeURIComponent(accountId)
            }
        });

        alert(response.data.message);

        if (response.data.status === 'SUCCESS') {
            return response.data.data;
        }
    } catch (error) {
        console.log(error);

        if (error.response && error.response.status === 400) {
            const errors = error.response.data.errors;

            const message = errors.map(e => e.defaultMessage).join('\n');

            alert(message);
        } else {
            alert('잠시 후 다시 시도해주세요.');
        }
    }
    return true; // 중복 여부 확인 실패 시 기본값 반환
}

/**
 * 회원가입을 axios 요청
 * @param userDto 회원정보
 * @returns {Promise<boolean>}
 */
export async function signUpUser(userDto) {
    try {
        const response = await instance.post("/api/v1/user", userDto);

        alert(response.data.message);
        return true; // 성공 시 true 반환
    } catch (error) {
        if (error.response && error.response.status === 400) {
            const errors = error.response.data.errors;

            const message = errors.map(e => e.defaultMessage).join('\n');

            alert(message);

            return false;
        } else {
            alert('잠시 후 다시 시도해주세요.');

            return false;
        }
    }
}

