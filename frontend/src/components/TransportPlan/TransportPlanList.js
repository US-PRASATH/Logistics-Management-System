import React, { useEffect, useState } from 'react';
import { getAllTransportPlans, deleteTransportPlan } from '../../api/TransportPlanService';
import TransportPlanForm from './TransportPlanForm'; // Assuming you have the form component

const TransportPlanList = () => {
    const [transportPlans, setTransportPlans] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedTransportPlan, setSelectedTransportPlan] = useState(null);

    useEffect(() => {
        fetchTransportPlans();
    }, []);

    const fetchTransportPlans = async () => {
        try {
            const response = await getAllTransportPlans();
            setTransportPlans(response.data);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching transport plans:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteTransportPlan(id);
            setTransportPlans(transportPlans.filter(plan => plan.id !== id)); // Remove the deleted plan from the list
        } catch (error) {
            console.error('Error deleting transport plan:', error);
        }
    };

    const handleEdit = (plan) => {
        setSelectedTransportPlan(plan); // Populate form with the selected transport plan
    };

    const handleTransportPlanUpdated = () => {
        fetchTransportPlans(); // Refresh the transport plan list after updating
        setSelectedTransportPlan(null); // Reset the selected plan
    };

    return (
        <div>
            <h2>Transport Plans</h2>
            {loading ? <p>Loading...</p> : (
                <ul>
                    {transportPlans.map((plan) => (
                        <li key={plan.id}>
                            <span>{plan.route} - {plan.carrier} (Capacity: {plan.loadCapacity})</span>
                            <button onClick={() => handleEdit(plan)}>Edit</button>
                            <button onClick={() => handleDelete(plan.id)}>Delete</button>
                        </li>
                    ))}
                </ul>
            )}
            <TransportPlanForm 
                selectedTransportPlan={selectedTransportPlan} 
                onTransportPlanAdded={fetchTransportPlans} 
                onTransportPlanUpdated={handleTransportPlanUpdated} 
            />
        </div>
    );
};

export default TransportPlanList;
