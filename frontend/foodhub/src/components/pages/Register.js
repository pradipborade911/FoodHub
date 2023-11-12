import React, { useCallback, useState } from 'react';
import { IP_ADDRS } from "../../service/BaseAddress";
import { useDropzone } from 'react-dropzone';
import axios from "axios";

const Register = () => {
  const dropzoneStyles = {
    border: '2px dashed #cccccc',
    borderRadius: '4px',
    padding: '20px',
    textAlign: 'center',
    cursor: 'pointer',
  };

  const [formData, setFormData] = useState({
    signUpRequest: {
      username: '',
      firstName: '',
      lastName: '',
      email: '',
      mobile: '',
      password: '',
      userRole: '',
      address: {
        line1: '',
        line2: '',
        city: '',
        pincode: '',
        state: '',
      }
    }
  });
  const [profilePic, setProfilePic] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      signUpRequest: {
        ...formData.signUpRequest,
        [name]: value,
      },
    });
  };


  const handleAddressChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      signUpRequest: {
        ...formData.signUpRequest,
        address: {
          ...formData.signUpRequest.address,
          [name]: value,
        },
      },
    });
  };

  const onDrop = useCallback((acceptedFiles) => {
    const file = acceptedFiles[0];
    setProfilePic(file);
  }, []);

  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const formDataToSend = new FormData();
      formDataToSend.append('profilePic', profilePic)
      formDataToSend.append('signUpRequest', new Blob([JSON.stringify(formData.signUpRequest)], { type: 'application/json' }));
      const response = await axios.post(`${IP_ADDRS}/signup`, formDataToSend);
      console.log('Registration successful:', response.data);
      //Redirect or perform other actions after successful registration
    } catch (error) {
      console.error('Registration failed:', error.message);
      // Handle errors, show messages, etc.
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Sign Up</h2>
      <form onSubmit={handleSubmit}>
        <div className="shadow p-3 mb-4 bg-body rounded row">
          <div className="shadow-sm p-3 mb-4 bg-body rounded col-sm-4">
          <div {...getRootProps()} style={dropzoneStyles}>
                <input {...getInputProps()} />
                <p>Drag & drop a profile picture here, or click to select one</p>
              </div>
              {profilePic && (
                <div>
                  <p>Uploaded file: {profilePic.name}</p>
                </div>
              )}
              </div>
          <div className="shadow-sm p-3 mb-4 bg-body rounded col-sm-4">
            {/* User Details Form - Right */}
            <div className="form-group">
              <label htmlFor="username">Username:</label>
              <input
                type="text"
                className="form-control"
                id="username"
                name="username"
                value={formData.signUpRequest.username}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="firstName">First Name:</label>
              <input
                type="text"
                className="form-control"
                id="firstName"
                name="firstName"
                value={formData.signUpRequest.firstName}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="lastName">Last Name:</label>
              <input
                type="text"
                className="form-control"
                id="lastName"
                name="lastName"
                value={formData.signUpRequest.lastName}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                type="email"
                className="form-control"
                id="email"
                name="email"
                value={formData.signUpRequest.email}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="mobile">Mobile:</label>
              <input
                type="text"
                className="form-control"
                id="mobile"
                name="mobile"
                value={formData.signUpRequest.mobile}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="inputState">User Role</label>
              <select
                id="userRole"
                className="form-control"
                name="userRole"
                value={formData.signUpRequest.userRole}
                onChange={handleChange}
              >
                <option value={null}>Choose...</option>
                <option value="VENDOR">Vendor</option>
                <option value="CUSTOMER">Customer</option>
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input
                type="password"
                className="form-control"
                id="password"
                name="password"
                value={formData.signUpRequest.password}
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="shadow-sm p-3 mb-4 bg-body rounded col-sm-4">
            <div className="form-group">
              <label htmlFor="inputAddress">Address</label>
              <input
                type="text"
                className="form-control"
                id="inputAddress"
                placeholder="1234 Main St"
                name="line1"
                value={formData.signUpRequest.address.line1}
                onChange={handleAddressChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="inputAddress2">Address 2</label>
              <input
                type="text"
                className="form-control"
                id="inputAddress2"
                placeholder="Apartment, studio, or floor"
                name="line2"
                value={formData.signUpRequest.address.line2}
                onChange={handleAddressChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="inputCity">City</label>
              <input
                type="text"
                className="form-control"
                id="city"
                name="city"
                value={formData.signUpRequest.address.city}
                onChange={handleAddressChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="inputState">State</label>
              <select
                id="state"
                className="form-control"
                name="state"
                value={formData.signUpRequest.address.state}
                onChange={handleAddressChange}
              >
                <option value={null}>Choose...</option>
                <option value="Maharashtra">Maharashtra</option>
                <option value="Delhi">Delhi</option>
                <option value="Gujrat">Gujrat</option>
                <option value="Uttar Pradesh">Uttar Pradesh</option>
                <option value="Karnataka">Karnataka</option>
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="inputZip">Zip</label>
              <input
                type="text"
                className="form-control"
                id="pincode"
                name="pincode"
                value={formData.signUpRequest.address.pincode}
                onChange={handleAddressChange}
              />
            </div>
          </div>

        </div>
        <div  style={{ float: 'right' }} className="">
            <button type="submit" className="btn btn-primary" style={{ backgroundColor: '#FF6347' }}>
              Sign Up
            </button>
          </div>
      </form>
    </div>
  );
};

export default Register;
