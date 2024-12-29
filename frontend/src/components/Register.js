// import { setupFromConfig } from 'karma/lib/logger';
import React,{useState, useContext} from 'react'
import { UserContext } from './UserContext';
import {useNavigate} from "react-router-dom";

const Register = () => {
    const [formData, setFormData] = useState(
    {
        username:"",
        email:"",
        password:"",
        confirmPassword:"",
        companyName:"",
    }
    );
    const navigate = useNavigate();
    const {setUser}=useContext(UserContext);
    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData({...formData,
        [name]:value,})
    }
    const handleSubmit = (e) => {
        e.preventDefault();
        if(formData.password===formData.confirmPassword){
            alert(`Registration successful`);
            setUser(formData);
            navigate("/login");
        }
        else{
            alert("Passwords do not match");
        }
    }
  return (
    <div>
        <h1>Register</h1>

        <form onSubmit={handleSubmit}>
            <label htmlFor="username">Username: </label>
            <input type='text' name="username" onChange={handleChange} value={formData.username}/><br/>
            <label htmlFor='email'>Email: </label>
            <input type="text" name="email" onChange={handleChange} value={formData.email}/><br/>
            <label htmlFor='password'>Password: </label>
            <input type='password' name="password" onChange={handleChange} value={formData.password}/><br/>
            <label htmlFor='confirmPassword'>Confirm Password: </label>
            <input type='password' name="confirmPassword" onChange={handleChange} value={formData.confirmPassword}/><br/>
            <label htmlFor='companyName'>Company Name: </label>
            <input type='text' name="companyName" onChange={handleChange} value={formData.companyName}/><br/>
            <input type='submit' value="Submit"/>
        </form> 
    </div>
  )
}

export default Register;