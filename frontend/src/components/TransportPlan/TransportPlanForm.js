import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/auth';

const TransportPlanForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [orders, setOrders] = useState([]);
  const [transporters, setTransporters] = useState([]);
  const [warehouses, setWarehouses] = useState([]);
  const [formData, setFormData] = useState({
    order: '',
    transporter: '',
    warehouse: '',
    loadCapacity: '',
    schedule: ''
  });
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [ordersRes, transportersRes, warehousesRes] = await Promise.all([
          api.get('/api/orders/transport-plans'),
          api.get('/api/transporters'),
          api.get('/api/warehouses')
        ]);
        
        setOrders(ordersRes.data);
        setTransporters(transportersRes.data);
        setWarehouses(warehousesRes.data);
      } catch (err) {
        console.error('Error fetching data:', err);
        setError('Failed to load required data');
      }
    };

    const fetchTransportPlan = async () => {
      if (id) {
        try {
          const response = await api.get(`/api/transport-plans/${id}`);
          const plan = response.data;
          
          // Format date for input
          const scheduleDate = plan.schedule ? new Date(plan.schedule) : new Date();
          const formattedDate = scheduleDate.toISOString().slice(0, 16);
          
          setFormData({
            order: plan.order?.id || '',
            transporter: plan.transporter?.id || '',
            warehouse: plan.warehouse?.id || '',
            loadCapacity: plan.loadCapacity || '',
            schedule: formattedDate
          });
        } catch (err) {
          console.error('Error fetching transport plan:', err);
          setError('Failed to load transport plan');
        }
      }
    };

    fetchData();
    fetchTransportPlan();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const transportPlanData = {
        order: { id: formData.order },
        transporter: { id: formData.transporter },
        warehouse: { id: formData.warehouse },
        loadCapacity: parseFloat(formData.loadCapacity),
        schedule: formData.schedule
      };

      if (id) {
        await api.put(`/api/transport-plans/${id}`, transportPlanData);
      } else {
        await api.post('/api/transport-plans', transportPlanData);
      }

      navigate('/transport-plans');
    } catch (err) {
      console.error('Error saving transport plan:', err);
      setError('Failed to save transport plan. Please check your input and try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h2 className="text-2xl font-bold leading-7 text-gray-900 sm:text-3xl sm:truncate">
            {id ? 'Edit Transport Plan' : 'Create New Transport Plan'}
          </h2>
        </div>
      </div>

      {error && (
        <div className="bg-red-50 p-4 rounded-md mb-6">
          <div className="flex">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor">
                <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
              </svg>
            </div>
            <div className="ml-3">
              <h3 className="text-sm font-medium text-red-800">{error}</h3>
            </div>
          </div>
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow-md">
        <div>
          <label htmlFor="order" className="block text-sm font-medium text-gray-700">
            Order
          </label>
          <select
            id="order"
            name="order"
            value={formData.order}
            onChange={handleChange}
            required
            className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          >
            <option value="">Select an order</option>
            {orders.map((order) => (
              <option key={order.id} value={order.id}>
                Order #{order.id} - {order.customerName} ({order.product?.name})
              </option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="transporter" className="block text-sm font-medium text-gray-700">
            Transporter
          </label>
          <select
            id="transporter"
            name="transporter"
            value={formData.transporter}
            onChange={handleChange}
            required
            className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          >
            <option value="">Select a transporter</option>
            {transporters.map((transporter) => (
              <option key={transporter.id} value={transporter.id}>
                {transporter.name} ({transporter.transporterType})
              </option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="warehouse" className="block text-sm font-medium text-gray-700">
            Warehouse
          </label>
          <select
            id="warehouse"
            name="warehouse"
            value={formData.warehouse}
            onChange={handleChange}
            required
            className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          >
            <option value="">Select a warehouse</option>
            {warehouses.map((warehouse) => (
              <option key={warehouse.id} value={warehouse.id}>
                {warehouse.name} ({warehouse.location})
              </option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="loadCapacity" className="block text-sm font-medium text-gray-700">
            Load Capacity
          </label>
          <input
            type="number"
            name="loadCapacity"
            id="loadCapacity"
            min="0"
            step="0.01"
            value={formData.loadCapacity}
            onChange={handleChange}
            required
            className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          />
        </div>

        <div>
          <label htmlFor="schedule" className="block text-sm font-medium text-gray-700">
            Schedule Date
          </label>
          <input
            type="datetime-local"
            name="schedule"
            id="schedule"
            value={formData.schedule}
            onChange={handleChange}
            required
            className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          />
        </div>

        <div className="flex justify-end space-x-3">
          <button
            type="button"
            onClick={() => navigate('/transport-plans')}
            className="inline-flex justify-center py-2 px-4 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={loading}
            className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:bg-blue-300"
          >
            {loading ? 'Saving...' : 'Save'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default TransportPlanForm;