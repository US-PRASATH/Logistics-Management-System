import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/auth';

const WarehouseItemForm = () => {
  const { warehouseId, id } = useParams(); // warehouseId fixed, id for editing warehouseItem
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  const [formData, setFormData] = useState({
    quantity: '',
    price: '',
    product: ''
  });

  // Fetch products list
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await api.get('/api/products');
        setProducts(response.data);
      } catch (err) {
        console.error('Error fetching products:', err);
        setError('Failed to load products');
      }
    };

    fetchProducts();
  }, []);

  // If editing, fetch warehouse item data by id and fetch price immediately
  useEffect(() => {
    const fetchWarehouseItem = async () => {
      if (id) {
        try {
          const response = await api.get(`/api/warehouses/${warehouseId}/warehouse-items/${id}`);
          const item = response.data;
          const productId = item.product?.id || '';

          setFormData({
            quantity: item.quantity || '',
            price: '',
            product: productId
          });

          if (productId) {
            const productRes = await api.get(`/api/products/${productId}`);
            setFormData(prev => ({
              ...prev,
              price: productRes.data.price
            }));
          }
        } catch (err) {
          console.error('Error fetching warehouse item:', err);
          setError('Failed to load warehouse item');
        }
      }
    };

    fetchWarehouseItem();
  }, [id, warehouseId]);

  // When product changes, fetch product details to auto-fill price
  useEffect(() => {
    const fetchProductDetails = async () => {
      if (formData.product) {
        try {
          const response = await api.get(`/api/products/${formData.product}`);
          const product = response.data;
          setFormData(prev => ({
            ...prev,
            price: product.price
          }));
        } catch (err) {
          console.error('Error fetching product details:', err);
          setError('Failed to load product details');
          setFormData(prev => ({ ...prev, price: '' }));
        }
      } else {
        // Clear price if no product selected
        setFormData(prev => ({ ...prev, price: '' }));
      }
    };

    fetchProductDetails();
  }, [formData.product]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const newQuantity = parseInt(formData.quantity, 10);
      if (isNaN(newQuantity) || newQuantity <= 0) {
        setError('Quantity must be a positive number');
        setLoading(false);
        return;
      }

      const payload = {
        quantity: newQuantity,
        warehouse: { id: warehouseId },
        product: { id: formData.product }
      };

      if (id) {
        await api.put(`/api/warehouses/${warehouseId}/warehouse-items/${id}`, payload);
      } else {
        const existingItemsResponse = await api.get(`/api/warehouses/${warehouseId}/warehouse-items`);
        const existingItems = existingItemsResponse.data;

        const existingItem = existingItems.find(item => item.product?.id === formData.product);

        if (existingItem) {
          const updatedItemData = {
            ...payload,
            quantity: existingItem.quantity + newQuantity
          };
          await api.put(`/api/warehouses/${warehouseId}/warehouse-items/${existingItem.id}`, updatedItemData);
        } else {
          await api.post(`/api/warehouses/${warehouseId}/warehouse-items`, payload);
        }
      }

      navigate(`/warehouses/${warehouseId}/warehouse-items`);
    } catch (err) {
      console.error('Error saving warehouse item:', err);
      setError('Failed to save warehouse item. Please check your input and try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h2 className="text-2xl font-bold leading-7 text-gray-900 sm:text-3xl sm:truncate">
            {id ? 'Edit Warehouse Item' : 'Add New Warehouse Item'}
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
          <label htmlFor="product" className="block text-sm font-medium text-gray-700">Product</label>
          <select
            id="product"
            name="product"
            value={formData.product}
            onChange={handleChange}
            required
            className="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          >
            <option value="">Select a product</option>
            {products.map(p => (
              <option key={p.id} value={p.id}>{p.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="price" className="block text-sm font-medium text-gray-700">Price</label>
          <div className="mt-1 relative rounded-md shadow-sm">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <span className="text-gray-500 sm:text-sm">₹</span>
            </div>
            <input
              type="number"
              name="price"
              id="price"
              min="0"
              step="0.01"
              value={formData.price}
              readOnly
              className="focus:ring-blue-500 focus:border-blue-500 block w-full pl-7 pr-12 sm:text-sm border-gray-300 rounded-md mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
              placeholder="0.00"
            />
          </div>
        </div>

        <div>
          <label htmlFor="quantity" className="block text-sm font-medium text-gray-700">Quantity</label>
          <input
            type="number"
            id="quantity"
            name="quantity"
            min="0"
            value={formData.quantity}
            onChange={handleChange}
            required
            className="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
          />
        </div>

        <div className="flex justify-end space-x-3">
          <button
            type="button"
            onClick={() => navigate(`/warehouses/${warehouseId}/warehouse-items`)}
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

export default WarehouseItemForm;
