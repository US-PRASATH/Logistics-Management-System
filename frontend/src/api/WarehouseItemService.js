// src/services/WarehouseItemService.js
import axios from 'axios';

const API_URL = 'https://8080-fcdcccabffdbaeb319887339abbeebabcabone.premiumproject.examly.io/api/warehouse-items'; // Replace with your actual backend URL

// Fetch all warehouse items
export const getAllWarehouseItems = () => {
    return axios.get(API_URL);
};

// Create a new warehouse item
export const createWarehouseItem = (warehouseItemData) => {
    return axios.post(API_URL, warehouseItemData);
};

// Update an existing warehouse item
export const updateWarehouseItem = (id, warehouseItemData) => {
    return axios.put(`${API_URL}/${id}`, warehouseItemData);
};

// Delete a warehouse item
export const deleteWarehouseItem = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};
