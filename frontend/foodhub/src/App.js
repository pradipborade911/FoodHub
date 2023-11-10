import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import React from 'react';
import './App.css';
import Login from './components/pages/Login.js';
import Profile from './components/pages/Profile.js';

function App() {
  return (
    <Router>
      <div className="App">
        {/* Routes should be rendered inside a Router */}
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
