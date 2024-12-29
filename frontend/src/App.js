// src/App.js
import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link, useNavigate } from 'react-router-dom';
import OrderList from './components/Order/OrderList';
import ShipmentList from './components/Shipment/ShipmentList'; // Import your ShipmentList component
import SupplierList from './components/Supplier/SupplierList'; // Example for Supplier component
import TransportPlanList from './components/TransportPlan/TransportPlanList';
import WarehouseItemList from './components/Warehouse/WarehouseItemList';
import Register from './components/Register';
import Login from './components/Login';
import { UserProvider } from './components/UserContext';

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();
    useEffect(()=>{
        if(!isLoggedIn && window.location.pathname === "/"){
        navigate("/login");
    }
    },[isLoggedIn])
    return (
        // <Router>
            <UserProvider>
            <div className="App">
                <h1>Logistics Management System</h1>
                {isLoggedIn && (<nav>
                    <Link to="/orders">Orders</Link> | 
                    <Link to="/shipments"> Shipments</Link> | 
                    <Link to="/suppliers">Suppliers</Link> | 
                    <Link to="/transport-plans">Transport Plans</Link> |
                    <Link to="/warehouse-items">Warehouse Items</Link> 
                </nav>)}

                <Routes>
                    <Route path="/orders" element={<OrderList />} />
                    <Route path="/shipments" element={<ShipmentList />} />
                    <Route path="/suppliers" element={<SupplierList />} />
                    <Route path="/transport-plans" element={<TransportPlanList />} />
                    <Route path="/warehouse-items" element={<WarehouseItemList />} />  
                    <Route path="/register" element={<Register/>} />
                    <Route path="/login" element={<Login setIsLoggedIn={(value)=>setIsLoggedIn}/>} />
                    {/* Add other routes here if necessary */}
                </Routes>
            </div>
            </UserProvider>
        // </Router>
    );
};

export default App;
