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
            setShipmentData(selectedShipment); 
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
                await updateShipment(selectedShipment.id, shipmentData);
                onShipmentUpdated();
            } else {
                await createShipment(shipmentData);
                onShipmentAdded();
            }

            
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
        <form onSubmit={handleSubmit} className="space-y-4 bg-white p-6 rounded-lg shadow-md">
            <input
                type="text"
                name="trackingNumber"
                value={shipmentData.trackingNumber}
                onChange={handleChange}
                placeholder="Tracking Number"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="origin"
                value={shipmentData.origin}
                onChange={handleChange}
                placeholder="Origin"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="destination"
                value={shipmentData.destination}
                onChange={handleChange}
                placeholder="Destination"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="status"
                value={shipmentData.status}
                onChange={handleChange}
                placeholder="Status"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="date"
                name="estimatedDeliveryDate"
                value={shipmentData.estimatedDeliveryDate}
                onChange={handleChange}
                placeholder="Estimated Delivery Date"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <div className="flex space-x-4">
                <button
                    type="submit"
                    className="px-4 py-2 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                    {selectedShipment ? "Update Shipment" : "Create Shipment"}
                </button>
                {selectedShipment && (
                    <button
                        type="button"
                        onClick={() => setShipmentData({
                            trackingNumber: '',
                            origin: '',
                            destination: '',
                            status: '',
                            estimatedDeliveryDate: '',
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

export default ShipmentForm;
