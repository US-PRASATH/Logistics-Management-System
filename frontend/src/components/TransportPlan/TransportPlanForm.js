import React, { useState, useEffect } from 'react';
import { createTransportPlan, updateTransportPlan } from '../../api/TransportPlanService';

const TransportPlanForm = ({ selectedTransportPlan, onTransportPlanAdded, onTransportPlanUpdated }) => {
    const [transportPlanData, setTransportPlanData] = useState({
        route: '',
        carrier: '',
        loadCapacity: '',
        schedule: '',
    });

    const [error, setError] = useState('');

   
    useEffect(() => {
        if (selectedTransportPlan) {
            setTransportPlanData({
                route: selectedTransportPlan.route || '',
                carrier: selectedTransportPlan.carrier || '',
                loadCapacity: selectedTransportPlan.loadCapacity || '',
                schedule: selectedTransportPlan.schedule || '',
            });
        } else {
           
            setTransportPlanData({
                route: '',
                carrier: '',
                loadCapacity: '',
                schedule: '',
            });
        }
    }, [selectedTransportPlan]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setTransportPlanData({
            ...transportPlanData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const { route, carrier, loadCapacity, schedule } = transportPlanData;

        // Validate required fields
        if (!route || !carrier || !loadCapacity || !schedule) {
            setError('All fields are required');
            return;
        }

        const transportPlanPayload = {
            route,
            carrier,
            loadCapacity: parseInt(loadCapacity, 10),
            schedule: new Date(schedule),
        };

        try {
            if (selectedTransportPlan) {
                await updateTransportPlan(selectedTransportPlan.id, transportPlanPayload);
                onTransportPlanUpdated();
            } else {
                await createTransportPlan(transportPlanPayload);
                onTransportPlanAdded();
            }

           
            setTransportPlanData({
                route: '',
                carrier: '',
                loadCapacity: '',
                schedule: '',
            });
            setError('');
        } catch (err) {
            setError('Error saving transport plan');
            console.error(err);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4 bg-white p-6 rounded-lg shadow-md">
            {error && <p className="text-red-500 text-sm">{error}</p>}
            <input
                type="text"
                name="route"
                value={transportPlanData.route}
                onChange={handleChange}
                placeholder="Route"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="text"
                name="carrier"
                value={transportPlanData.carrier}
                onChange={handleChange}
                placeholder="Carrier"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="number"
                name="loadCapacity"
                value={transportPlanData.loadCapacity}
                onChange={handleChange}
                placeholder="Load Capacity"
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
                type="datetime-local"
                name="schedule"
                value={transportPlanData.schedule}
                onChange={handleChange}
                required
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <div className="flex space-x-4">
                <button
                    type="submit"
                    className="px-4 py-2 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                    {selectedTransportPlan ? 'Update Transport Plan' : 'Add Transport Plan'}
                </button>
                {selectedTransportPlan && (
                    <button
                        type="button"
                        onClick={() =>
                            setTransportPlanData({
                                route: '',
                                carrier: '',
                                loadCapacity: '',
                                schedule: '',
                            })
                        }
                        className="px-4 py-2 bg-gray-600 text-white font-semibold rounded-md hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
                    >
                        Cancel
                    </button>
                )}
            </div>
        </form>
    );
};

export default TransportPlanForm;
