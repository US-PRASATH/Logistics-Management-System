import React, { useState, useEffect } from 'react';
import { createWarehouseItem, updateWarehouseItem } from '../../api/WarehouseItemService';

const WarehouseItemForm = ({ selectedItem, onItemAdded, onItemUpdated }) => {
    const [itemData, setItemData] = useState({
        itemName: '',
        category: '',
        quantity: '',
        storageLocation: '',
    });

    useEffect(() => {
        if (selectedItem) {
            setItemData({
                itemName: selectedItem.itemName || '',
                category: selectedItem.category || '',
                quantity: selectedItem.quantity || '',
                storageLocation: selectedItem.storageLocation || '',
            });
        } else {
            setItemData({
                itemName: '',
                category: '',
                quantity: '',
                storageLocation: '',
            });
        }
    }, [selectedItem]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setItemData({
            ...itemData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            if (selectedItem) {
                await updateWarehouseItem(selectedItem.id, itemData);
                onItemUpdated();
            } else {
                await createWarehouseItem(itemData);
                onItemAdded();
            }

            // Reset form on successful submission
            setItemData({
                itemName: '',
                category: '',
                quantity: '',
                storageLocation: '',
            });
        } catch (error) {
            console.error('Error saving warehouse item:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4 bg-white p-6 rounded-lg shadow-md">
            {/* <h3 className="text-lg font-semibold mb-4">
                {selectedItem ? 'Edit Warehouse Item' : 'Add New Warehouse Item'}
            </h3> */}
            <input
                type="text"
                name="itemName"
                value={itemData.itemName}
                onChange={handleChange}
                placeholder="Item Name"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="category"
                value={itemData.category}
                onChange={handleChange}
                placeholder="Category"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="number"
                name="quantity"
                value={itemData.quantity}
                onChange={handleChange}
                placeholder="Quantity"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="storageLocation"
                value={itemData.storageLocation}
                onChange={handleChange}
                placeholder="Storage Location"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <div className="flex space-x-4">
                <button
                    type="submit"
                    className="px-4 py-2 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                    {selectedItem ? 'Update Item' : 'Add Item'}
                </button>
                {selectedItem && (
                    <button
                        type="button"
                        onClick={() =>
                            setItemData({
                                itemName: '',
                                category: '',
                                quantity: '',
                                storageLocation: '',
                            })
                        }
                        className="px-4 py-2 bg-gray-600 text-white font-semibold rounded-md hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
                    >
                        Cancel
                    </button>
                )}
            </div>
        </form>
    );
};

export default WarehouseItemForm;
