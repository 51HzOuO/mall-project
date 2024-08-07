import axios from "axios";

axios.defaults.baseURL = 'http://localhost:8080';

/**
 * @returns {Promise<axios.AxiosResponse<any>>}
 * 预期的返回值：
 * [
 *     {
 *         "id": 1,
 *         "name": "可达鸭",
 *         "company": "com",
 *         "price": 321.00,
 *         "sales": 0,
 *         "stock": 123,
 *         "imgPath": "test"
 *     },
 *     {
 *         "id": 10,
 *         "name": "123",
 *         "company": "321",
 *         "price": 3.00,
 *         "sales": 0,
 *         "stock": 2,
 *         "imgPath": "test"
 *     }
 * ]
 */
export const getAllTableData = () => axios.get('/getAllFurn')

/**
 * @param upload FormData
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const uploadFurnImg = (upload) => axios.post('/uploadFurnImg', upload, {
    headers: {
        'Content-Type': 'multipart/form-data'
    }
});

export const addFurn = (formData) => axios.post('/addFurn', formData, {
    headers: {
        'Content-Type': 'application/json'
    }
});

export const deleteFurn = (id) => axios.delete(`/deleteFurn?id=${id}`)

export const getFurnImg = (url) => axios.get(`/getFurnImg?url=${url}`)
