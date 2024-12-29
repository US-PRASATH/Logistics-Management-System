// src/components/OrderList.js
import React, { useEffect, useState } from 'react';
import { getOrders, deleteOrder } from '../../api/orderService';
import OrderForm from './OrderForm';

const OrderList = () => {
    const [orders, setOrders] = useState([]);
    const [selectedOrder, setSelectedOrder] = useState(null);

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            const response = await getOrders();
            console.log(response);
            setOrders(response.data);
        } catch (error) {
            console.error("Error fetching orders:", error);
        }
    };

    const handleDelete = async (id) => {
        await deleteOrder(id);
        fetchOrders();
    };

    const handleEdit = (order) => {
        setSelectedOrder(order);
    };

    return (
        <div>
            <h2>Orders</h2>
            <OrderForm fetchOrders={fetchOrders} selectedOrder={selectedOrder} setSelectedOrder={setSelectedOrder} />
            <ul>
                {orders.map(order => (
                    <li key={order.id}>
                        {order.customerName} - {order.product} - {order.quantity} - {order.status}
                        <button onClick={() => handleEdit(order)}>Edit</button>
                        <button onClick={() => handleDelete(order.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default OrderList;
