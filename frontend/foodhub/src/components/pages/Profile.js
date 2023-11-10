import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

const UserProfile = () => {
  console.log('Component rendering...');
  const navigate = useNavigate();

  const [user, setUser] = useState();

  useEffect(() => {
    console.log('inside useEffect...');
    const loggedInUser = localStorage.getItem('user');
    if (loggedInUser) {
      console.log('User found!');
      const foundUser = JSON.parse(loggedInUser);
      setUser(foundUser);
    } else {
      console.log('User not found! Navigating to login...');
      navigate(`/login`);
    }
  }, [navigate]);

  // Conditionally render based on whether user is defined
  if (!user) {
    return null; // or render a loading state
  }

  return (
    <div className="container mt-5">
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">User Profile</h5>
          <img
            src={user.profilePic ? `data:image/jpeg;base64,${user.profilePic}` : 'path_to_default_image'}
            alt="Profile"
            className="img-fluid rounded-circle mb-3"
          />
          <p className="card-text">
            <strong>Name:</strong> {user.firstName} {user.lastName}
          </p>
          <p className="card-text">
            <strong>Email:</strong> {user.email}
          </p>
          <p className="card-text">
            <strong>Mobile:</strong> {user.mobile}
          </p>
          <p className="card-text">
            <strong>User Role:</strong> {user.userRole}
          </p>
          <p className="card-text">
            <strong>Address:</strong> {user.address}
          </p>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;
