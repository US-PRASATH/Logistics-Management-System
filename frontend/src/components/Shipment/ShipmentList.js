// src/components/Shipment/ShipmentList.js
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
            setShipments(shipments.filter(shipment => shipment.id !== id)); // Remove the deleted shipment from the list
        } catch (error) {
            console.error('Error deleting shipment:', error);
        }
    };

    const handleEdit = (shipment) => {
        setSelectedShipment(shipment);  // Populate form with the selected shipment
    };

    const handleShipmentUpdated = () => {
        fetchShipments(); // Refresh the shipment list after updating
        setSelectedShipment(null); // Reset the selected shipment
    };

    return (
        <div>
            <h2>Shipments</h2>
            {loading ? <p>Loading...</p> : (
                <ul>
                    {shipments.map((shipment) => (
                        <li key={shipment.id}>
                            <span>{shipment.trackingNumber} - {shipment.origin} to {shipment.destination}</span>
                            <button onClick={() => handleEdit(shipment)}>Edit</button>
                            <button onClick={() => handleDelete(shipment.id)}>Delete</button>
                        </li>
                    ))}
                </ul>
            )}
            <ShipmentForm 
                selectedShipment={selectedShipment} 
                onShipmentAdded={fetchShipments} 
                onShipmentUpdated={handleShipmentUpdated} 
            />
        </div>
    );
};

export default ShipmentList;
