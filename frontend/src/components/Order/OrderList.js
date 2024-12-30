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
        <div className="p-6 bg-gray-100 min-h-screen">
            <h2 className="text-2xl font-bold mb-4">Orders</h2>
            <OrderForm fetchOrders={fetchOrders} selectedOrder={selectedOrder} setSelectedOrder={setSelectedOrder} />
            
            <div className="overflow-x-auto mt-6">
                <table className="min-w-full bg-white border rounded-lg shadow-md">
                    <thead>
                        <tr>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Customer Name</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Product</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Quantity</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Status</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map((order) => (
                            <tr key={order.id} className="border-t">
                                <td className="px-6 py-4 text-gray-700">{order.customerName}</td>
                                <td className="px-6 py-4 text-gray-700">{order.product}</td>
                                <td className="px-6 py-4 text-gray-700">{order.quantity}</td>
                                <td className="px-6 py-4 text-gray-700">{order.status}</td>
                                <td className="px-6 py-4 text-gray-700 space-x-4">
                                    <button
                                        onClick={() => handleEdit(order)}
                                        className="px-4 py-2 bg-yellow-500 text-white font-semibold rounded-md hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-yellow-500"
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleDelete(order.id)}
                                        className="px-4 py-2 bg-red-500 text-white font-semibold rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default OrderList;
