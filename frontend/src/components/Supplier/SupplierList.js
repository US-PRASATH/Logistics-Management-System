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
            setSuppliers(suppliers.filter(supplier => supplier.id !== id)); 
        } catch (error) {
            console.error('Error deleting supplier:', error);
        }
    };

    const handleEdit = (supplier) => {
        setSelectedSupplier(supplier); 
    };

    const handleSupplierUpdated = () => {
        fetchSuppliers(); 
        setSelectedSupplier(null);
    };

    return (
        <div className="p-6 bg-gray-50 min-h-screen">
            <h2 className="text-2xl font-bold mb-4">Suppliers</h2>
            <SupplierForm
                selectedSupplier={selectedSupplier}
                onSupplierAdded={fetchSuppliers}
                onSupplierUpdated={handleSupplierUpdated}
            />
            <div className="overflow-x-auto mt-6">
                <table className="min-w-full bg-white border rounded-lg shadow-md">
                    <thead>
                        <tr>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Name</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Contact Info</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Address</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Performance Rating</th>
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
                            {suppliers.map((supplier) => (
                                <tr key={supplier.id} className="border-t">
                                    <td className="px-6 py-4 text-gray-700">{supplier.name}</td>
                                    <td className="px-6 py-4 text-gray-700">{supplier.contactInfo}</td>
                                    <td className="px-6 py-4 text-gray-700">{supplier.address}</td>
                                    <td className="px-6 py-4 text-gray-700">{supplier.performanceRating}</td>
                                    <td className="px-6 py-4 text-gray-700 space-x-4">
                                        <button
                                            onClick={() => handleEdit(supplier)}
                                            className="px-4 py-2 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                                        >
                                            Edit
                                        </button>
                                        <button
                                            onClick={() => handleDelete(supplier.id)}
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

export default SupplierList;
