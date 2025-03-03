import React, { useState, useEffect } from 'react';
import { Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import './App.css';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';
import Dashboard from './components/Dashboard';
import Navbar from './components/Navbar';
import OrderList from './components/Order/OrderList';
import OrderForm from './components/Order/OrderForm';
import ProductList from './components/Product/ProductList';
import ProductForm from './components/Product/ProductForm';
import SupplierList from './components/Supplier/SupplierList';
import SupplierForm from './components/Supplier/SupplierForm';
import TransporterList from './components/Transporter/TransporterList';
import TransporterForm from './components/Transporter/TransporterForm';
import WarehouseList from './components/Warehouse/WarehouseItemList';
import WarehouseForm from './components/Warehouse/WarehouseItemForm';
import ShipmentList from './components/Shipment/ShipmentList';
import ShipmentDetails from './components/Shipment/ShipmentForm';
// import TransportPlanList from './components/transport-plans/TransportPlanList';
// import TransportPlanForm from './components/transport-plans/TransportPlanForm';
import ProtectedRoute from './components/Auth/ProtectedRoute';
import { AuthProvider } from './components/context/AuthContext';

function App() {
  return (
    <AuthProvider>
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="container mx-auto px-4 py-8">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            
            <Route path="/" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            } />
            
            <Route path="/orders" element={
              <ProtectedRoute>
                <OrderList />
              </ProtectedRoute>
            } />
            <Route path="/orders/new" element={
              <ProtectedRoute>
                <OrderForm />
              </ProtectedRoute>
            } />
            <Route path="/orders/:id" element={
              <ProtectedRoute>
                <OrderForm />
              </ProtectedRoute>
            } />
            
            <Route path="/products" element={
              <ProtectedRoute>
                <ProductList />
              </ProtectedRoute>
            } />
            <Route path="/products/new" element={
              <ProtectedRoute>
                <ProductForm />
              </ProtectedRoute>
            } />
            <Route path="/products/:id" element={
              <ProtectedRoute>
                <ProductForm />
              </ProtectedRoute>
            } />
            
            <Route path="/suppliers" element={
              <ProtectedRoute>
                <SupplierList />
              </ProtectedRoute>
            } />
            <Route path="/suppliers/new" element={
              <ProtectedRoute>
                <SupplierForm />
              </ProtectedRoute>
            } />
            <Route path="/suppliers/:id" element={
              <ProtectedRoute>
                <SupplierForm />
              </ProtectedRoute>
            } />
            
            <Route path="/transporters" element={
              <ProtectedRoute>
                <TransporterList />
              </ProtectedRoute>
            } />
            <Route path="/transporters/new" element={
              <ProtectedRoute>
                <TransporterForm />
              </ProtectedRoute>
            } />
            <Route path="/transporters/:id" element={
              <ProtectedRoute>
                <TransporterForm />
              </ProtectedRoute>
            } />
            
            <Route path="/warehouses" element={
              <ProtectedRoute>
                <WarehouseList />
              </ProtectedRoute>
            } />
            <Route path="/warehouses/new" element={
              <ProtectedRoute>
                <WarehouseForm />
              </ProtectedRoute>
            } />
            <Route path="/warehouses/:id" element={
              <ProtectedRoute>
                <WarehouseForm />
              </ProtectedRoute>
            } />
            
            <Route path="/shipments" element={
              <ProtectedRoute>
                <ShipmentList />
              </ProtectedRoute>
            } />
            <Route path="/shipments/:id" element={
              <ProtectedRoute>
                <ShipmentDetails />
              </ProtectedRoute>
            } />
            
            {/* <Route path="/transport-plans" element={
              <ProtectedRoute>
                <TransportPlanList />
              </ProtectedRoute>
            } />
            <Route path="/transport-plans/new" element={
              <ProtectedRoute>
                <TransportPlanForm />
              </ProtectedRoute>
            } />
            <Route path="/transport-plans/:id" element={
              <ProtectedRoute>
                <TransportPlanForm />
              </ProtectedRoute>
            } /> */}
          </Routes>
        </div>
      </div>
    </AuthProvider>
  );
}

export default App;