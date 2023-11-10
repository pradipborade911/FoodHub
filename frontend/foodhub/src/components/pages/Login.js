import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { IP_ADDRS } from "../../service/BaseAddress"
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

const SignIn = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSignIn = async e => {
    e.preventDefault();
    try {
      const response = await axios.post(`${IP_ADDRS}/login`, { username, password });
      // store the user in localStorage
      localStorage.setItem('user', JSON.stringify(response.data));
      navigate(`/profile`);
    } catch (error) {
      // Handle errors
      console.error('Sign-in failed:', error.message);
    }
  };

  return (
    <div>
      <label>
        Username:
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
      </label>
      <br />
      <label>
        Password:
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      </label>
      <br />
      <button onClick={handleSignIn}>Sign In</button>
    </div>
  );
};

export default SignIn;
