// src/components/WarehouseItem/WarehouseItemList.js
import React, { useEffect, useState } from 'react';
import { getAllWarehouseItems, deleteWarehouseItem } from '../../api/WarehouseItemService'; // Correct import
import WarehouseItemForm from './WarehouseItemForm'; // Make sure this exists

const WarehouseItemList = () => {
    const [warehouseItems, setWarehouseItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedItem, setSelectedItem] = useState(null); // Track selected item for edit

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
            setWarehouseItems(warehouseItems.filter(item => item.id !== id)); // Remove the deleted item
        } catch (error) {
            console.error('Error deleting warehouse item:', error);
        }
    };

    const handleEdit = (item) => {
        setSelectedItem(item);  // Populate form with the selected item data
    };

    const handleItemUpdated = () => {
        fetchWarehouseItems(); // Refresh list after item update
        setSelectedItem(null); // Reset selected item
    };

    return (
        <div>
            <h2>Warehouse Items</h2>
            {loading ? <p>Loading...</p> : (
                <ul>
                    {warehouseItems.map((item) => (
                        <li key={item.id}>
                            <span>{item.itemName} ({item.category}) - {item.quantity} items</span>
                            <button onClick={() => handleEdit(item)}>Edit</button>
                            <button onClick={() => handleDelete(item.id)}>Delete</button>
                        </li>
                    ))}
                </ul>
            )}
            <WarehouseItemForm 
                selectedItem={selectedItem} 
                onItemAdded={fetchWarehouseItems} 
                onItemUpdated={handleItemUpdated} 
            />
        </div>
    );
};

export default WarehouseItemList;
