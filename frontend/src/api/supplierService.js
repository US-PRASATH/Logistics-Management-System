// src/services/supplierService.js
import axios from 'axios';

// const API_URL = 'https://8080-fcdcccabffdbaeb319887339abbeebabcabone.premiumproject.examly.io/api/suppliers'; // Replace with your actual backend URL

const API_URL = 'http://localhost:8080/api/suppliers';
export const getAllSuppliers = () => {
    return axios.get(API_URL);
};

export const createSupplier = (supplierData) => {
    return axios.post(API_URL, supplierData);
};

export const updateSupplier = (id, supplierData) => {
    return axios.put(`${API_URL}/${id}`, supplierData);
};

export const deleteSupplier = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};
