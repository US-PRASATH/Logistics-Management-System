import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
// import api from '../../services/api';
import api from '../api/auth';

const WarehouseList = () => {
  const [warehouses, setWarehouses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWarehouses = async () => {
      try {
        const response = await api.get('/api/warehouses');
        setWarehouses(response.data);
        setLoading(false);
      } catch (err) {
        console.error('Error fetching warehouses:', err);
        setError('Failed to load warehouses');
        setLoading(false);
      }
    };

    fetchWarehouses();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this warehouse?')) {
      try {
        await api.delete(`/api/warehouses/${id}`);
        setWarehouses(warehouses.filter(warehouse => warehouse.id !== id));
      } catch (err) {
        console.error('Error deleting warehouse:', err);
        setError('Failed to delete warehouse');
      }
    }
  };

  const getCapacityPercentage = (warehouse) => {
    if (!warehouse.capacity || warehouse.capacity === 0) return 0;
    const used = warehouse.capacity - (warehouse.remainingCapacity || 0);
    return Math.round((used / warehouse.capacity) * 100);
  };

  const getCapacityColorClass = (percentage) => {
    if (percentage >= 90) return 'bg-red-500';
    if (percentage >= 75) return 'bg-yellow-500';
    return 'bg-green-500';
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-50 p-4 rounded-md">
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
    );
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Warehouses</h1>
        <Link
          to="/warehouses/new"
          className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
        >
          Add New Warehouse
        </Link>
      </div>

      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {warehouses.length === 0 ? (
          <div className="col-span-full text-center py-12 bg-white rounded-lg shadow">
            <p className="text-gray-500">No warehouses found</p>
          </div>
        ) : (
          warehouses.map((warehouse) => {
            const capacityPercentage = getCapacityPercentage(warehouse);
            const capacityColorClass = getCapacityColorClass(capacityPercentage);
            
            return (
              <div key={warehouse.id} className="bg-white overflow-hidden shadow rounded-lg">
                <div className="px-4 py-5 sm:p-6">
                  <div className="flex items-center justify-between">
                    <h3 className="text-lg leading-6 font-medium text-gray-900">{warehouse.name}</h3>
                    <div className="flex space-x-2">
                      <Link
                        to={`/warehouses/${warehouse.id}`}
                        className="text-blue-600 hover:text-blue-900"
                      >
                        Edit
                      </Link>
                      <button
                        onClick={() => handleDelete(warehouse.id)}
                        className="text-red-600 hover:text-red-900"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                  <div className="mt-2">
                    <p className="text-sm text-gray-500">{warehouse.location}</p>
                  </div>
                  <div className="mt-4">
                    <div className="flex items-center justify-between text-sm">
                      <span>Capacity Usage</span>
                      <span>{capacityPercentage}%</span>
                    </div>
                    <div className="mt-1 relative pt-1">
                      <div className="overflow-hidden h-2 text-xs flex rounded bg-gray-200">
                        <div
                          style={{ width: `${capacityPercentage}%` }}
                          className={`shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center ${capacityColorClass}`}
                        ></div>
                      </div>
                    </div>
                    <div className="mt-2 flex justify-between text-sm text-gray-600">
                      <span>Total: {warehouse.capacity || 0}</span>
                      <span>Available: {warehouse.remainingCapacity || 0}</span>
                    </div>
                  </div>
                </div>
                <div className="bg-gray-50 px-4 py-4 sm:px-6">
                  <Link
                    to="/warehouse-items"
                    className="text-sm font-medium text-blue-600 hover:text-blue-500"
                  >
                    View inventory
                  </Link>
                </div>
              </div>
            );
          })
        )}
      </div>
    </div>
  );
};

export default WarehouseList;