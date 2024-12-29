// src/components/Shipment/ShipmentForm.js
import React, { useState, useEffect } from 'react';
import { createShipment, updateShipment } from '../../api/shipmentService';

const ShipmentForm = ({ onShipmentAdded, selectedShipment, onShipmentUpdated }) => {
    const [shipmentData, setShipmentData] = useState({
        trackingNumber: '',
        origin: '',
        destination: '',
        status: '',
        estimatedDeliveryDate: '',
    });

    useEffect(() => {
        if (selectedShipment) {
            setShipmentData(selectedShipment);  // Populate form fields with selected shipment data
        }
    }, [selectedShipment]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setShipmentData({
            ...shipmentData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (selectedShipment) {
                // Update existing shipment
                await updateShipment(selectedShipment.id, shipmentData);
                onShipmentUpdated();  // Refresh shipment list after update
            } else {
                // Create new shipment
                await createShipment(shipmentData);
                onShipmentAdded(); // Refresh the shipment list after adding
            }

            // Reset the form after submission
            setShipmentData({
                trackingNumber: '',
                origin: '',
                destination: '',
                status: '',
                estimatedDeliveryDate: '',
            });
        } catch (error) {
            console.error('Error submitting shipment:', error);
        }
    };

    return (
        <div>
            <h2>{selectedShipment ? 'Edit Shipment' : 'Add New Shipment'}</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Tracking Number:
                    <input
                        type="text"
                        name="trackingNumber"
                        value={shipmentData.trackingNumber}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Origin:
                    <input
                        type="text"
                        name="origin"
                        value={shipmentData.origin}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Destination:
                    <input
                        type="text"
                        name="destination"
                        value={shipmentData.destination}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Status:
                    <input
                        type="text"
                        name="status"
                        value={shipmentData.status}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Estimated Delivery Date:
                    <input
                        type="date"
                        name="estimatedDeliveryDate"
                        value={shipmentData.estimatedDeliveryDate}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <button type="submit">{selectedShipment ? 'Update Shipment' : 'Add Shipment'}</button>
            </form>
        </div>
    );
};

export default ShipmentForm;
