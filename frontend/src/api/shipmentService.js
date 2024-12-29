// src/services/shipmentService.js
import axios from 'axios';

const API_URL = 'https://8080-fcdcccabffdbaeb319887339abbeebabcabone.premiumproject.examly.io/api/shipments'; // Replace with your actual backend URL

export const getAllShipments = () => {
    return axios.get(API_URL);
};

export const createShipment = (shipmentData) => {
    return axios.post(API_URL, shipmentData);
};

export const updateShipment = (id, shipmentData) => {
    return axios.put(`${API_URL}/${id}`, shipmentData);
};

export const deleteShipment = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};
