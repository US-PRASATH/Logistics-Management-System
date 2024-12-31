import React, { useEffect, useState } from 'react';
import { getAllShipments, deleteShipment } from '../../api/shipmentService';
import ShipmentForm from './ShipmentForm';

const ShipmentList = () => {
    const [shipments, setShipments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedShipment, setSelectedShipment] = useState(null);

    useEffect(() => {
        fetchShipments();
    }, []);

    const fetchShipments = async () => {
        try {
            const response = await getAllShipments();
            setShipments(response.data);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching shipments:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteShipment(id);
            setShipments(shipments.filter(shipment => shipment.id !== id)); 
        } catch (error) {
            console.error('Error deleting shipment:', error);
        }
    };

    const handleEdit = (shipment) => {
        setSelectedShipment(shipment);  
    };

    const handleShipmentUpdated = () => {
        fetchShipments(); 
        setSelectedShipment(null); 
    };

    return (
        <div className="p-6 bg-gray-100 min-h-screen">
            <h2 className="text-2xl font-bold mb-4">Shipments</h2>
            <ShipmentForm 
                selectedShipment={selectedShipment} 
                onShipmentAdded={fetchShipments} 
                onShipmentUpdated={handleShipmentUpdated} 
            />
            
            
                <div className="overflow-x-auto mt-6">
                    <table className="min-w-full bg-white border rounded-lg shadow-md">
                        <thead>
                            <tr>
                                <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Tracking Number</th>
                                <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Origin</th>
                                <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Destination</th>
                                <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Status</th>
                                <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Estimated Delivery Date</th>
                                <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Actions</th>
                            </tr>
                        </thead>
                        {loading ? (
                <tbody>
                <tr>
                    <td colSpan="6" className="px-6 py-4 text-center text-gray-600 mx-auto">Loading...</td>
                </tr>
            </tbody>
            ) : (<tbody>
                            {shipments.map((shipment) => (
                                <tr key={shipment.id} className="border-t">
                                    <td className="px-6 py-4 text-gray-700">{shipment.trackingNumber}</td>
                                    <td className="px-6 py-4 text-gray-700">{shipment.origin}</td>
                                    <td className="px-6 py-4 text-gray-700">{shipment.destination}</td>
                                    <td className="px-6 py-4 text-gray-700">{shipment.status}</td>
                                    <td className="px-6 py-4 text-gray-700">{shipment.estimatedDeliveryDate}</td>
                                    <td className="px-6 py-4 text-gray-700 space-x-4">
                                        <button
                                            onClick={() => handleEdit(shipment)}
                                            className="px-4 py-2 bg-yellow-500 text-white font-semibold rounded-md hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-yellow-500"
                                        >
                                            Edit
                                        </button>
                                        <button
                                            onClick={() => handleDelete(shipment.id)}
                                            className="px-4 py-2 bg-red-500 text-white font-semibold rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody> )}
                    </table>
                </div>
           

            
        </div>
    );
};

export default ShipmentList;
