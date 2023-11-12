import React, { Component } from 'react';
import { Navigate } from 'react-router-dom';

class NavbarComponent extends Component {
    state = {
        isMenuOpen: false,
        pincode: '',
        isPincodeValid: false,
        isSubmit: false,
    };

    toggleMenu = () => {
        this.setState({ isMenuOpen: !this.state.isMenuOpen });
    };

    handleSubmit = (event) => {
        // event.preventDefault();
        // const { pincode } = this.state;

        if (this.state.isPincodeValid) {
            this.setState({ isSubmit: true });
        }
    };

    handlePincodeChange = (e) => {
        /*const pincode = e.target.value;
        const isPincodeValid = validatePincode(pincode);
        this.setState({ pincode, isPincodeValid });*/
    };

    render() {
        const { isMenuOpen, isSubmit, pincode, isPincodeValid } = this.state;

        return (
            <nav className="navbar navbar-expand-lg navbar-light" style={{
                backgroundColor: '#FF6347', position: 'sticky',
                top: 0,
            }}>
                <a className="navbar-brand" href="/" style={{ fontSize: '28px', color: '#fff', fontWeight: 'bold' }}>
                    FoodHuB
                </a>

                <button
                    className={`navbar-toggler ${isMenuOpen ? '' : 'collapsed'}`}
                    type="button"
                    data-toggle="collapse"
                    data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent"
                    aria-expanded={isMenuOpen ? 'true' : 'false'}
                    aria-label="Toggle navigation"
                    onClick={this.toggleMenu}
                >
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className={`collapse navbar-collapse ${isMenuOpen ? 'show' : ''}`} id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            <a className="nav-link" href="http://localhost:3000" onClick={this.toggleMenu}>
                                Home <span className="sr-only">(current)</span>
                            </a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="http://localhost:3000#about-us" onClick={this.toggleMenu}>
                                About us
                            </a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="http://localhost:3000/signup" onClick={this.toggleMenu}>
                                Sign up
                            </a>
                        </li>
                        <li className="nav-item">
                                <a className="nav-link darker-link" href="http://localhost:3000/sign-in" onClick={this.toggleMenu}>
                                    Sign In
                                </a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link darker-link" href="http://localhost:3000#footer" onClick={this.toggleMenu}>
                                Contact us
                            </a>
                        </li>
                    </ul>

                        {/* Search Form */}
                    <form className="form-inline my-2 my-lg-0">
                            {/* Pincode Input */}
                        <input
                            className={`form-control mr-sm-2 ${!isPincodeValid ? 'is-invalid' : ''}`}
                            type="search"
                            placeholder="Enter your pincode"
                            aria-label="Search"
                            value={pincode}
                            onChange={this.handlePincodeChange}
                        />
                        <button
                            className="btn btn-outline-success my-2 my-sm-0"
                            type="button"
                            onClick={this.handleSubmit}
                            style={{ backgroundColor: '#fff', color: '#FF6347' }}
                        >
                            Find Delivery partners near you
                        </button>

                            {/* Conditional Navigation */}
                        {isSubmit && isPincodeValid && <Navigate to={`/vendorsList/${pincode}`} replace />}
                    </form>
                </div>
            </nav>
        );
    }
}

export default NavbarComponent;
