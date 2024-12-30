import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link, useNavigate } from 'react-router-dom';
import OrderList from './components/Order/OrderList';
import ShipmentList from './components/Shipment/ShipmentList';
import SupplierList from './components/Supplier/SupplierList';
import TransportPlanList from './components/TransportPlan/TransportPlanList';
import WarehouseItemList from './components/Warehouse/WarehouseItemList';
import Register from './components/Register';
import Login from './components/Login';
import { UserProvider } from './components/UserContext';
import ProtectedRoute from './components/ProtectedRoute';

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    // Redirect to login if not logged in and trying to access home
    if (!isLoggedIn && window.location.pathname === "/") {
      navigate("/login");
    }
  }, [isLoggedIn]);

  return (
    <UserProvider>
      <div className="App">
      <header className="bg-blue-800 text-white py-4 shadow-lg">
          <div className="container mx-auto flex justify-between items-center">
            <h1 className="text-3xl font-semibold">Logistics Management System</h1>
            
            {isLoggedIn && (
              <div className="flex items-center space-x-4">
                <nav className="flex space-x-6">
                  <Link to="/orders" className="hover:bg-blue-700 px-4 py-2 rounded-md">Orders</Link>
                  <Link to="/shipments" className="hover:bg-blue-700 px-4 py-2 rounded-md">Shipments</Link>
                  <Link to="/suppliers" className="hover:bg-blue-700 px-4 py-2 rounded-md">Suppliers</Link>
                  <Link to="/transport-plans" className="hover:bg-blue-700 px-4 py-2 rounded-md">Transport Plans</Link>
                  <Link to="/warehouse-items" className="hover:bg-blue-700 px-4 py-2 rounded-md">Warehouse Items</Link>
                </nav>
                
                <button 
                  onClick={() => { setIsLoggedIn(false); navigate('/login'); }}
                  className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-md"
                >
                  Logout
                </button>
              </div>
            )}
          </div>
        </header>
        <Routes>
          {/* Public Routes */}
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />

          {/* Protected Routes */}
          <Route
            path="/orders"
            element={
              <ProtectedRoute isLoggedIn={isLoggedIn}>
                <OrderList />
              </ProtectedRoute>
            }
          />
          <Route
            path="/shipments"
            element={
              <ProtectedRoute isLoggedIn={isLoggedIn}>
                <ShipmentList />
              </ProtectedRoute>
            }
          />
          <Route
            path="/suppliers"
            element={
              <ProtectedRoute isLoggedIn={isLoggedIn}>
                <SupplierList />
              </ProtectedRoute>
            }
          />
          <Route
            path="/transport-plans"
            element={
              <ProtectedRoute isLoggedIn={isLoggedIn}>
                <TransportPlanList />
              </ProtectedRoute>
            }
          />
          <Route
            path="/warehouse-items"
            element={
              <ProtectedRoute isLoggedIn={isLoggedIn}>
                <WarehouseItemList />
              </ProtectedRoute>
            }
          />
        </Routes>
      </div>
    </UserProvider>
  );
};

export default App;
