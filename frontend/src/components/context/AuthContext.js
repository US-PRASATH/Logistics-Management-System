import React, { createContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
// import api from '../services/api';
import api from '../api/auth';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const initAuth = async () => {
      if (token) {
        try {
          // Set the token in axios headers
          console.log(token);
          api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          
          // You could add a user profile endpoint to fetch user data
          // const response = await api.get('/api/auth/profile');
          // setCurrentUser(response.data);
          
          // For now, we'll just set a basic user object
          setCurrentUser({ authenticated: true });
        } catch (error) {
          console.error('Error initializing auth:', error);
          logout();
        }
      }
      setLoading(false);
    };

    initAuth();
  }, [token]);

  const login = async (credentials) => {
    try {
      const response = await api.post('/api/auth/login', credentials);
      const { data } = response;
      
      // Store the token
      console.log(data);
      localStorage.setItem('token', data);
      setToken(data);
      
      // Set axios default header
      api.defaults.headers.common['Authorization'] = `Bearer ${data}`;
      
      // Set current user
      setCurrentUser({ authenticated: true });
      
      return { success: true };
    } catch (error) {
      console.error('Login error:', error);
      return { 
        success: false, 
        message: error.response?.data?.message || 'Invalid credentials' 
      };
    }
  };

  const register = async (userData) => {
    try {
      const response = await api.post('/api/auth/register', userData);
      return { success: true };
    } catch (error) {
      console.error('Registration error:', error);
      return { 
        success: false, 
        message: error.response?.data?.message || 'Registration failed' 
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setCurrentUser(null);
    delete api.defaults.headers.common['Authorization'];
    navigate('/login');
  };

  const value = {
    currentUser,
    token,
    loading,
    login,
    register,
    logout,
    isAuthenticated: !!currentUser
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
};