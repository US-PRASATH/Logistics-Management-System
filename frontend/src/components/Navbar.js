import React, { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from './context/AuthContext';

const Navbar = () => {
  const { currentUser, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  if (!currentUser) {
    return null; // Don't show navbar for unauthenticated users
  }

  const toggleMobileMenu = () => {
    setMobileMenuOpen(!mobileMenuOpen);
  };

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
              <Link to="/restocking-requests" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Restocking
              </Link>
              <Link to="/expenses" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Expenses
              </Link>
              <Link to="/revenue" className="hover:text-blue-200 px-3 py-2 rounded-md text-sm font-medium">
                Revenue
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
            <button
              onClick={toggleMobileMenu}
              className="md:hidden ml-4 text-white focus:outline-none"
            >
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                {mobileMenuOpen ? (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                ) : (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                )}
              </svg>
            </button>
          </div>
        </div>
        
        {/* Mobile menu - shown on small screens */}
        {mobileMenuOpen && (
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
              <Link to="/restocking-requests" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
                Restocking
              </Link>
              <Link to="/expenses" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
                Expenses
              </Link>
              <Link to="/revenue" className="hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
                Revenue
              </Link>
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;