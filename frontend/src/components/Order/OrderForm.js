import React, { useState, useEffect } from 'react';
import { createOrder, updateOrder } from '../../api/orderService';

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
        <form onSubmit={handleSubmit} className="space-y-4 bg-white p-6 rounded-lg shadow-md">
            <input
                type="text"
                name="customerName"
                value={form.customerName}
                onChange={handleChange}
                placeholder="Customer Name"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="product"
                value={form.product}
                onChange={handleChange}
                placeholder="Product"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="number"
                name="quantity"
                value={form.quantity}
                onChange={handleChange}
                placeholder="Quantity"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="status"
                value={form.status}
                onChange={handleChange}
                placeholder="Status"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <div className="flex space-x-4">
                <button
                    type="submit"
                    className="px-4 py-2 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                    {selectedOrder ? "Update Order" : "Create Order"}
                </button>
                {selectedOrder && (
                    <button
                        type="button"
                        onClick={() => setSelectedOrder(null)}
                        className="px-4 py-2 bg-gray-600 text-white font-semibold rounded-md hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
                    >
                        Cancel
                    </button>
                )}
            </div>
        </form>
    );
};

export default OrderForm;
