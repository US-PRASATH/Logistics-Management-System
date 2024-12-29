// src/components/OrderForm.js
import React, { useState, useEffect } from 'react';
//import { createOrder, updateOrder } from './api/orderService';
import { createOrder,updateOrder } from '../../api/orderService';

const OrderForm = ({ fetchOrders, selectedOrder, setSelectedOrder }) => {
    const [form, setForm] = useState({ customerName: '', product: '', quantity: '', status: '' });

    useEffect(() => {
        if (selectedOrder) {
            setForm(selectedOrder);
        }
    }, [selectedOrder]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (selectedOrder) {
                await updateOrder(selectedOrder.id, form);
                setSelectedOrder(null);
            } else {
                await createOrder(form);
            }
            setForm({ customerName: '', product: '', quantity: '', status: '' });
            fetchOrders();
        } catch (error) {
            console.error("Error creating/updating order:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="customerName" value={form.customerName} onChange={handleChange} placeholder="Customer Name" required />
            <input type="text" name="product" value={form.product} onChange={handleChange} placeholder="Product" required />
            <input type="number" name="quantity" value={form.quantity} onChange={handleChange} placeholder="Quantity" required />
            <input type="text" name="status" value={form.status} onChange={handleChange} placeholder="Status" required />
            <button type="submit">{selectedOrder ? "Update Order" : "Create Order"}</button>
            {selectedOrder && <button onClick={() => setSelectedOrder(null)}>Cancel</button>}
        </form>
    );
};

export default OrderForm;
