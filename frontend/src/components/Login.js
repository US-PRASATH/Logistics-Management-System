import React, { useState, useContext } from 'react';
import { UserContext } from './UserContext';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import axios from 'axios';

const Login = (props) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const { user } = useContext(UserContext);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/api/auth/login",{
      "username":formData.username,
      "password":formData.password
    }).then((response)=>{
      localStorage.setItem("token",response.data);
      props.setIsLoggedIn(true);
      navigate("/");
    });
    // if (user && formData.username === user.username && formData.password === user.password) {
    //   props.setIsLoggedIn(true);
    //   navigate("/");
    // } else {
    //   alert("Invalid credentials");
    // }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 " style={{ backgroundImage: 'url("https://images.pexels.com/photos/906494/pexels-photo-906494.jpeg")', backgroundSize: 'cover', backgroundPosition: 'center' }}>
      <div className="w-full max-w-md p-8 space-y-8 bg-white shadow-lg rounded-lg">
        <h2 className="text-2xl font-bold text-center text-gray-800">Login</h2>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label htmlFor="username" className="block text-sm font-medium text-gray-700">Username</label>
            <input
              type="text"
              name="username"
              id="username"
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
              onChange={handleChange}
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">Password</label>
            <input
              type="password"
              name="password"
              id="password"
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
              onChange={handleChange}
            />
          </div>

          <div>
            <input
              type="submit"
              value="Login"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            />
          </div>
        </form>
        <p>New User? <Link to="/register" className='text-indigo-500 hover:text-red-500 hover:underline active:font-bold active:text-indigo-500 visited:text-purple-500'>Click Here to Sign Up</Link> </p>
      </div>

    </div>
  );
};

export default Login;
