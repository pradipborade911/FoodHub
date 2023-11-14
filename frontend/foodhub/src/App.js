import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import React from 'react';
import './App.css';
import Login from './components/pages/Login.js';
import Profile from './components/pages/Profile.js';
import Navbar from './components/navbar/NavbarComponent.jsx'
import SignUp from './components/pages/Register';
import Lists from './components/pages/Lists';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        {/* Routes should be rendered inside a Router */}
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/admin" element={<Lists />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
