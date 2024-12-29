// src/components/WarehouseItem/WarehouseItemForm.js
import React, { useState, useEffect } from 'react';
import { createWarehouseItem, updateWarehouseItem } from '../../api/WarehouseItemService';

const WarehouseItemForm = ({ selectedItem, onItemAdded, onItemUpdated }) => {
    const [itemName, setItemName] = useState('');
    const [category, setCategory] = useState('');
    const [quantity, setQuantity] = useState('');
    const [storageLocation, setStorageLocation] = useState('');

    useEffect(() => {
        if (selectedItem) {
            setItemName(selectedItem.itemName);
            setCategory(selectedItem.category);
            setQuantity(selectedItem.quantity);
            setStorageLocation(selectedItem.storageLocation);
        }
    }, [selectedItem]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const itemData = { itemName, category, quantity, storageLocation };

        try {
            if (selectedItem) {
                // Update existing item
                await updateWarehouseItem(selectedItem.id, itemData);
                onItemUpdated();
            } else {
                // Create a new item
                await createWarehouseItem(itemData);
                onItemAdded();
            }
            // Reset form after submission
            setItemName('');
            setCategory('');
            setQuantity('');
            setStorageLocation('');
        } catch (error) {
            console.error('Error saving warehouse item:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h3>{selectedItem ? 'Edit Warehouse Item' : 'Add New Warehouse Item'}</h3>
            <div>
                <label>Item Name:</label>
                <input 
                    type="text" 
                    value={itemName} 
                    onChange={(e) => setItemName(e.target.value)} 
                    required 
                />
            </div>
            <div>
                <label>Category:</label>
                <input 
                    type="text" 
                    value={category} 
                    onChange={(e) => setCategory(e.target.value)} 
                    required 
                />
            </div>
            <div>
                <label>Quantity:</label>
                <input 
                    type="number" 
                    value={quantity} 
                    onChange={(e) => setQuantity(e.target.value)} 
                    required 
                />
            </div>
            <div>
                <label>Storage Location:</label>
                <input 
                    type="text" 
                    value={storageLocation} 
                    onChange={(e) => setStorageLocation(e.target.value)} 
                    required 
                />
            </div>
            <button type="submit">{selectedItem ? 'Update Item' : 'Add Item'}</button>
        </form>
    );
};

export default WarehouseItemForm;
