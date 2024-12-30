import React, { useEffect, useState } from 'react';
import { getAllTransportPlans, deleteTransportPlan } from '../../api/TransportPlanService';
import TransportPlanForm from './TransportPlanForm';

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
        <div className="p-6 bg-gray-100 min-h-screen">
            <h2 className="text-2xl font-bold mb-4">Transport Plans</h2>
            <TransportPlanForm 
                selectedTransportPlan={selectedTransportPlan} 
                onTransportPlanAdded={fetchTransportPlans} 
                onTransportPlanUpdated={handleTransportPlanUpdated} 
            />
            <div className="overflow-x-auto mt-6">
                <table className="min-w-full bg-white border rounded-lg shadow-md">
                    <thead>
                        <tr>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Route</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Carrier</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Load Capacity</th>
                            <th className="px-6 py-3 border-b-2 text-left text-sm font-semibold text-gray-600">Schedule</th>
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
                            {transportPlans.map((plan) => (
                                <tr key={plan.id} className="border-t">
                                    <td className="px-6 py-4 text-gray-700">{plan.route}</td>
                                    <td className="px-6 py-4 text-gray-700">{plan.carrier}</td>
                                    <td className="px-6 py-4 text-gray-700">{plan.loadCapacity}</td>
                                    <td className="px-6 py-4 text-gray-700">{new Date(plan.schedule).toLocaleString()}</td>
                                    <td className="px-6 py-4 text-gray-700 space-x-4">
                                        <button
                                            onClick={() => handleEdit(plan)}
                                            className="px-4 py-2 bg-yellow-500 text-white font-semibold rounded-md hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-yellow-500"
                                        >
                                            Edit
                                        </button>
                                        <button
                                            onClick={() => handleDelete(plan.id)}
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

export default TransportPlanList;
