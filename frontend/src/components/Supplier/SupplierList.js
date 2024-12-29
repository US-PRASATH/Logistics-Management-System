// src/components/Supplier/SupplierList.js
import React, { useEffect, useState } from 'react';
import { getAllSuppliers, deleteSupplier } from '../../api/supplierService';
import SupplierForm from './SupplierForm';

const SupplierList = () => {
    const [suppliers, setSuppliers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedSupplier, setSelectedSupplier] = useState(null);

    useEffect(() => {
        fetchSuppliers();
    }, []);

    const fetchSuppliers = async () => {
        try {
            const response = await getAllSuppliers();
            setSuppliers(response.data);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching suppliers:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteSupplier(id);
            setSuppliers(suppliers.filter(supplier => supplier.id !== id)); // Remove the deleted supplier from the list
        } catch (error) {
            console.error('Error deleting supplier:', error);
        }
    };

    const handleEdit = (supplier) => {
        setSelectedSupplier(supplier);  // Populate form with the selected supplier data
    };

    const handleSupplierUpdated = () => {
        fetchSuppliers(); // Refresh the supplier list after updating
        setSelectedSupplier(null); // Reset the selected supplier
    };

    return (
        <div>
            <h2>Suppliers</h2>
            {loading ? <p>Loading...</p> : (
                <ul>
                    {suppliers.map((supplier) => (
                        <li key={supplier.id}>
                            <span>{supplier.name} - {supplier.contactInfo}</span>
                            <button onClick={() => handleEdit(supplier)}>Edit</button>
                            <button onClick={() => handleDelete(supplier.id)}>Delete</button>
                        </li>
                    ))}
                </ul>
            )}
            <SupplierForm 
                selectedSupplier={selectedSupplier} 
                onSupplierAdded={fetchSuppliers} 
                onSupplierUpdated={handleSupplierUpdated} 
            />
        </div>
    );
};

export default SupplierList;
