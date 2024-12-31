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
            setSupplierData(selectedSupplier); 
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
                await updateSupplier(selectedSupplier.id, supplierData);
                onSupplierUpdated(); 
            } else {
                await createSupplier(supplierData);
                onSupplierAdded();
            }

            
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
        <form onSubmit={handleSubmit} className="space-y-4 bg-white p-6 rounded-lg shadow-md">
            <input
                type="text"
                name="name"
                value={supplierData.name}
                onChange={handleChange}
                placeholder="Name"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="contactInfo"
                value={supplierData.contactInfo}
                onChange={handleChange}
                placeholder="Contact Info"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="address"
                value={supplierData.address}
                onChange={handleChange}
                placeholder="Address"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="performanceRating"
                value={supplierData.performanceRating}
                onChange={handleChange}
                placeholder="Performance Rating"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <div className="flex space-x-4">
                <button
                    type="submit"
                    className="px-4 py-2 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                    {selectedSupplier ? 'Update Supplier' : 'Add Supplier'}
                </button>
                {selectedSupplier && (
                    <button
                        type="button"
                        onClick={() => setSupplierData({
                            name: '',
                            contactInfo: '',
                            address: '',
                            performanceRating: '',
                        })}
                        className="px-4 py-2 bg-gray-600 text-white font-semibold rounded-md hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
                    >
                        Cancel
                    </button>
                )}
            </div>
        </form>
    );
};

export default SupplierForm;
