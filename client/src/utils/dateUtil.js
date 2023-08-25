import dayjs from "dayjs";

/**
 * 날짜 형식을 변환하는 메서드
 *
 * @param date
 * @returns {string}
 */
export function formatDate(date) {
    return dayjs(date).format('YYYY.MM.DD HH:mm');
}

/**
 * 7일 이내에 해당하는지 알려주는 메서드
 *
 * @param date
 * @returns {boolean}
 */
export function isNew(date) {
    const diffInDays = dayjs().diff(date, 'day');

    return diffInDays <= 7;
}
