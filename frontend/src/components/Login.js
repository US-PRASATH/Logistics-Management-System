// import { setupFromConfig } from 'karma/lib/logger';
import React,{useState, useContext} from 'react';
import { UserContext } from './UserContext';
import { useNavigate } from 'react-router-dom';

const Login = (props) => {
    const [formData, setFormData] = useState(
    {
        username:"",
        password:"",
    }
    );

    const {user} = useContext(UserContext);
    const navigate = useNavigate();

    
    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData({...formData,
        [name]:value,})
    }
    const handleSubmit = (e) => {
        e.preventDefault();
        if(user && formData.username===user.username && formData.password===user.password){
            navigate("/");
            props.setIsLoggedIn(true);
            // alert("Registration successful for ${formData.username}")
        }
        else{
            alert("Invalid credentials");
        }
    }
  return (
    <div className='bg-gray-900'>
        <h1>Login</h1>

        <form onSubmit={handleSubmit}>
            <label htmlFor="username">Username: </label>
            <input type='text' name="username" onChange={handleChange}/><br/>
            <label htmlFor='password'>Password: </label>
            <input type='password' name="password" onChange={handleChange}/><br/>
            <input type='submit' value="Submit"/>
        </form> 
    </div>
  )
}

export default Login;