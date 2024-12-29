// src/components/Supplier/SupplierForm.js
import React, { useState, useEffect } from 'react';
import { createSupplier, updateSupplier } from '../../api/supplierService';

const SupplierForm = ({ onSupplierAdded, selectedSupplier, onSupplierUpdated }) => {
    const [supplierData, setSupplierData] = useState({
        name: '',
        contactInfo: '',
        address: '',
        performanceRating: '',
    });

    useEffect(() => {
        if (selectedSupplier) {
            setSupplierData(selectedSupplier); // Populate form fields with selected supplier data
        }
    }, [selectedSupplier]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSupplierData({
            ...supplierData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (selectedSupplier) {
                // Update existing supplier
                await updateSupplier(selectedSupplier.id, supplierData);
                onSupplierUpdated(); // Refresh supplier list after update
            } else {
                // Create new supplier
                await createSupplier(supplierData);
                onSupplierAdded(); // Refresh the supplier list after adding
            }

            // Reset the form after submission
            setSupplierData({
                name: '',
                contactInfo: '',
                address: '',
                performanceRating: '',
            });
        } catch (error) {
            console.error('Error submitting supplier:', error);
        }
    };

    return (
        <div>
            <h2>{selectedSupplier ? 'Edit Supplier' : 'Add New Supplier'}</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input
                        type="text"
                        name="name"
                        value={supplierData.name}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Contact Info:
                    <input
                        type="text"
                        name="contactInfo"
                        value={supplierData.contactInfo}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Address:
                    <input
                        type="text"
                        name="address"
                        value={supplierData.address}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Performance Rating:
                    <input
                        type="text"
                        name="performanceRating"
                        value={supplierData.performanceRating}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <button type="submit">{selectedSupplier ? 'Update Supplier' : 'Add Supplier'}</button>
            </form>
        </div>
    );
};

export default SupplierForm;
