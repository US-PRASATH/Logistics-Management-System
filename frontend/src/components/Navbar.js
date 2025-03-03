import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from './context/AuthContext';

const Navbar = () => {
  const { currentUser, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  if (!currentUser) {
    return null; // Don't show navbar for unauthenticated users
  }

  return (
    <nav className="bg-blue-800 text-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          <div className="flex items-center space-x-4">
            <Link to="/" className="text-xl font-bold">Logistics Management</Link>
            
            <div className="hidden md:flex space-x-4">
              <Link to="/orders" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Orders
              </Link>
              <Link to="/products" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Products
              </Link>
              <Link to="/suppliers" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Suppliers
              </Link>
              <Link to="/transporters" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Transporters
              </Link>
              <Link to="/warehouses" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Warehouses
              </Link>
              <Link to="/shipments" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Shipments
              </Link>
              <Link to="/transport-plans" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Transport Plans
              </Link>
            </div>
          </div>
          
          <div className="flex items-center">
            <button
              onClick={logout}
              className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md text-sm font-medium"
            >
              Logout
            </button>
          </div>
        </div>
        
        {/* Mobile menu - shown on small screens */}
        <div className="md:hidden py-2">
          <div className="flex flex-col space-y-2">
            <Link to="/orders" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
              Orders
            </Link>
            <Link to="/products" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
              Products
            </Link>
            <Link to="/suppliers" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
              Suppliers
            </Link>
            <Link to="/transporters" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
              Transporters
            </Link>
            <Link to="/warehouses" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
              Warehouses
            </Link>
            <Link to="/shipments" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
              Shipments
            </Link>
            <Link to="/transport-plans" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
              Transport Plans
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;