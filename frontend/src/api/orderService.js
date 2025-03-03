// src/api/orderService.js
import axios from 'axios';

// const API_URL = 'https://8080-fcdcccabffdbaeb319887339abbeebabcabone.premiumproject.examly.io/api/orders';

const API_URL = 'http://localhost:8080/api/orders';
const token = localStorage.getItem("token");
const config = {
    headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
    },
};
export const getOrders = () => axios.get(API_URL,config);

export const getOrderById = (id) => axios.get(`${API_URL}/${id}`,config);

export const createOrder = (order) => axios.post(API_URL, order,config);

export const updateOrder = (id, order) => axios.put(`${API_URL}/${id}`, order,config);

export const deleteOrder = (id) => axios.delete(`${API_URL}/${id}`,config);
