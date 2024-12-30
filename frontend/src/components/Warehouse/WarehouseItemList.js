import React, { useEffect, useState } from 'react';
import { getAllWarehouseItems, deleteWarehouseItem } from '../../api/WarehouseItemService';
import WarehouseItemForm from './WarehouseItemForm';

const WarehouseItemList = () => {
    const [warehouseItems, setWarehouseItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedItem, setSelectedItem] = useState(null);

    useEffect(() => {
        fetchWarehouseItems();
    }, []);

    const fetchWarehouseItems = async () => {
        try {
            const response = await getAllWarehouseItems();
            setWarehouseItems(response.data);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching warehouse items:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteWarehouseItem(id);
            setWarehouseItems(warehouseItems.filter(item => item.id !== id));
        } catch (error) {
            console.error('Error deleting warehouse item:', error);
        }
    };

    const handleEdit = (item) => {
        setSelectedItem(item);
    };

    const handleItemUpdated = () => {
        fetchWarehouseItems();
        setSelectedItem(null);
    };

    return (
        <div className="p-6 bg-gray-100 min-h-screen">
            <h2 className="text-2xl font-bold mb-4">Warehouse Items</h2>
            <WarehouseItemForm
                selectedItem={selectedItem}
                onItemAdded={fetchWarehouseItems}
                onItemUpdated={handleItemUpdated}
            />
            <div className="overflow-x-auto mt-6">
                <table className="min-w-full bg-white border rounded-lg shadow-md">
                    <thead>
                        <tr>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Item Name</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Category</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Quantity</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Storage Location</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Actions</th>
                        </tr>
                    </thead>
                    {loading ? (
                        <tbody>
                            <tr>
                                <td colSpan="5" className="px-6 py-4 text-center text-gray-600">Loading...</td>
                            </tr>
                        </tbody>
                    ) : (
                        <tbody>
                            {warehouseItems.map((item) => (
                                <tr key={item.id} className="border-t">
                                    <td className="px-6 py-4 text-gray-700">{item.itemName}</td>
                                    <td className="px-6 py-4 text-gray-700">{item.category}</td>
                                    <td className="px-6 py-4 text-gray-700">{item.quantity}</td>
                                    <td className="px-6 py-4 text-gray-700">{item.storageLocation}</td>
                                    <td className="px-6 py-4 text-gray-700 space-x-4">
                                        <button
                                            onClick={() => handleEdit(item)}
                                            className="px-4 py-2 bg-yellow-500 text-white font-semibold rounded-md hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-yellow-500"
                                        >
                                            Edit
                                        </button>
                                        <button
                                            onClick={() => handleDelete(item.id)}
                                            className="px-4 py-2 bg-red-500 text-white font-semibold rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    )}
                </table>
            </div>
        </div>
    );
};

export default WarehouseItemList;
