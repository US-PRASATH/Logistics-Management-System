import React, { useState, useEffect } from 'react';
import { createTransportPlan, updateTransportPlan } from '../../api/TransportPlanService';

const TransportPlanForm = ({ selectedTransportPlan, onTransportPlanAdded, onTransportPlanUpdated }) => {
    const [route, setRoute] = useState('');
    const [carrier, setCarrier] = useState('');
    const [loadCapacity, setLoadCapacity] = useState('');
    const [schedule, setSchedule] = useState('');
    const [error, setError] = useState('');

    // If a transport plan is selected, populate the form with its data
    useEffect(() => {
        if (selectedTransportPlan) {
            setRoute(selectedTransportPlan.route);
            setCarrier(selectedTransportPlan.carrier);
            setLoadCapacity(selectedTransportPlan.loadCapacity);
            setSchedule(selectedTransportPlan.schedule);
        } else {
            // Clear the form when no transport plan is selected
            setRoute('');
            setCarrier('');
            setLoadCapacity('');
            setSchedule('');
        }
    }, [selectedTransportPlan]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validation for required fields
        if (!route || !carrier || !loadCapacity || !schedule) {
            setError('All fields are required');
            return;
        }

        const transportPlanData = {
            route,
            carrier,
            loadCapacity: parseInt(loadCapacity),
            schedule: new Date(schedule), // Convert to Date format if necessary
        };

        try {
            if (selectedTransportPlan) {
                // Update existing transport plan
                await updateTransportPlan(selectedTransportPlan.id, transportPlanData);
                onTransportPlanUpdated(); // Notify parent component to refresh list
            } else {
                // Create new transport plan
                await createTransportPlan(transportPlanData);
                onTransportPlanAdded(); // Notify parent component to refresh list
            }
            // Clear form after successful submission
            setRoute('');
            setCarrier('');
            setLoadCapacity('');
            setSchedule('');
            setError('');
        } catch (error) {
            setError('Error saving transport plan');
            console.error(error);
        }
    };

    return (
        <div>
            <h3>{selectedTransportPlan ? 'Edit Transport Plan' : 'Add Transport Plan'}</h3>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Route</label>
                    <input
                        type="text"
                        value={route}
                        onChange={(e) => setRoute(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Carrier</label>
                    <input
                        type="text"
                        value={carrier}
                        onChange={(e) => setCarrier(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Load Capacity</label>
                    <input
                        type="number"
                        value={loadCapacity}
                        onChange={(e) => setLoadCapacity(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Schedule</label>
                    <input
                        type="datetime-local"
                        value={schedule}
                        onChange={(e) => setSchedule(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">
                    {selectedTransportPlan ? 'Update Transport Plan' : 'Add Transport Plan'}
                </button>
            </form>
        </div>
    );
};

export default TransportPlanForm;
