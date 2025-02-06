import axios from 'axios';

// const API_URL = 'https://8080-fcdcccabffdbaeb319887339abbeebabcabone.premiumproject.examly.io/api/transport-plans'; // Replace with your actual backend URL

const API_URL = 'http://localhost:8080/api/transport-plans';
// Function to get all transport plans
export const getAllTransportPlans = () => {
    return axios.get(API_URL);
};

// Function to create a new transport plan
export const createTransportPlan = (transportPlanData) => {
    return axios.post(API_URL, transportPlanData);
};

// Function to update an existing transport plan
export const updateTransportPlan = (id, transportPlanData) => {
    return axios.put(`${API_URL}/${id}`, transportPlanData);
};

// Function to delete a transport plan by ID
export const deleteTransportPlan = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};
