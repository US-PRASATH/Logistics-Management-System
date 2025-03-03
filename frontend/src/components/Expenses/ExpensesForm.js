import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/auth';

const ExpensesForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [transporters, setTransporters] = useState([]);
  const [warehouses, setWarehouses] = useState([]);
  const [suppliers, setSuppliers] = useState([]);
  const [formData, setFormData] = useState({
    amount: '',
    date: new Date().toISOString().split('T')[0],
    expenseType: 'TRANSPORTATION',
    transporter: '',
    warehouse: '',
    supplier: ''
  });
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [transportersRes, warehousesRes, suppliersRes] = await Promise.all([
          api.get('/api/transporters'),
          api.get('/api/warehouses'),
          api.get('/api/suppliers')
        ]);
        
        setTransporters(transportersRes.data);
        setWarehouses(warehousesRes.data);
        setSuppliers(suppliersRes.data);
      } catch (err) {
        console.error('Error fetching data:', err);
        setError('Failed to load required data');
      }
    };

    const fetchExpense = async () => {
      if (id) {
        try {
          const response = await api.get(`/api/expenses/${id}`);
          const expense = response.data;
          
          setFormData({
            amount: expense.amount || '',
            date: expense.date ? new Date(expense.date).toISOString().split('T')[0] : new Date().toISOString().split('T')[0],
            expenseType: expense.expenseType || 'TRANSPORTATION',
            transporter: expense.transporter?.id || '',
            warehouse: expense.warehouse?.id || '',
            supplier: expense.supplier?.id || ''
          });
        } catch (err) {
          console.error('Error fetching expense:', err);
          setError('Failed to load expense');
        }
      }
    };

    fetchData();
    fetchExpense();
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
      const expenseData = {
        amount: parseFloat(formData.amount),
        date: formData.date,
        expenseType: formData.expenseType,
        transporter: formData.expenseType === 'TRANSPORTATION' && formData.transporter ? { id: formData.transporter } : null,
        warehouse: formData.expenseType === 'WAREHOUSE' && formData.warehouse ? { id: formData.warehouse } : null,
        supplier: formData.expenseType === 'SUPPLIER_PAYMENT' && formData.supplier ? { id: formData.supplier } : null
      };

      if (id) {
        await api.put(`/api/expenses/${id}`, expenseData);
      } else {
        await api.post('/api/expenses', expenseData);
      }

      navigate('/expenses');
    } catch (err) {
      console.error('Error saving expense:', err);
      setError('Failed to save expense. Please check your input and try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h2 className="text-2xl font-bold leading-7 text-gray-900 sm:text-3xl sm:truncate">
            {id ? 'Edit Expense' : 'Add New Expense'}
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
          <label htmlFor="amount" className="block text-sm font-medium text-gray-700">
            Amount
          </label>
          <div className="mt-1 relative rounded-md shadow-sm">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <span className="text-gray-500 sm:text-sm">$</span>
            </div>
            <input
              type="number"
              name="amount"
              id="amount"
              min="0"
              step="0.01"
              value={formData.amount}
              onChange={handleChange}
              required
              className="focus:ring-blue-500 focus:border-blue-500 block w-full pl-7 pr-12 sm:text-sm border-gray-300 rounded-md"
              placeholder="0.00"
            />
          </div>
        </div>

        <div>
          <label htmlFor="date" className="block text-sm font-medium text-gray-700">
            Date
          </label>
          <input
            type="date"
            name="date"
            id="date"
            value={formData.date}
            onChange={handleChange}
            required
            className="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
          />
        </div>

        <div>
          <label htmlFor="expenseType" className="block text-sm font-medium text-gray-700">
            Expense Type
          </label>
          <select
            id="expenseType"
            name="expenseType"
            value={formData.expenseType}
            onChange={handleChange}
            className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          >
            <option value="TRANSPORTATION">Transportation</option>
            <option value="WAREHOUSE">Warehouse</option>
            <option value="SUPPLIER_PAYMENT">Supplier Payment</option>
            <option value="MISCELLANEOUS">Miscellaneous</option>
          </select>
        </div>

        {formData.expenseType === 'TRANSPORTATION' && (
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
                  {transporter.name}
                </option>
              ))}
            </select>
          </div>
        )}

        {formData.expenseType === 'WAREHOUSE' && (
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
                  {warehouse.name}
                </option>
              ))}
            </select>
          </div>
        )}

        {formData.expenseType === 'SUPPLIER_PAYMENT' && (
          <div>
            <label htmlFor="supplier" className="block text-sm font-medium text-gray-700">
              Supplier
            </label>
            <select
              id="supplier"
              name="supplier"
              value={formData.supplier}
              onChange={handleChange}
              required
              className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
            >
              <option value="">Select a supplier</option>
              {suppliers.map((supplier) => (
                <option key={supplier.id} value={supplier.id}>
                  {supplier.name}
                </option>
              ))}
            </select>
          </div>
        )}

        <div className="flex justify-end space-x-3">
          <button
            type="button"
            onClick={() => navigate('/expenses')}
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

export default ExpensesForm;