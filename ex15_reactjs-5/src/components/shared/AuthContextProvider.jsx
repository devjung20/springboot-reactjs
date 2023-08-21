import axios from 'axios';
import { createContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PropTypes } from 'prop-types';
import jwt_decode from 'jwt-decode';

const AuthContext = createContext();

export const AuthContextProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    if (localStorage.getItem('tokens')) {
      let tokens = JSON.parse(localStorage.getItem('tokens'));
      return jwt_decode(tokens);
    }
    return null;
  });

  const navigate = useNavigate();

  const login = async (payload) => {
    const apiResponse = await axios.post('http://localhost:2000/login', payload);
    console.log(apiResponse);
    localStorage.setItem('tokens', JSON.stringify(apiResponse.data));
    setUser(jwt_decode(apiResponse.data));
    navigate('/');
  };

  const logout = async () => {
    localStorage.removeItem('tokens');
    setUser(null);
    navigate('/');
  };

  return <AuthContext.Provider value={{ user, login, logout }}>{children}</AuthContext.Provider>;
};

AuthContextProvider.propTypes = {
  children: PropTypes.any.isRequired,
};

export default AuthContext;
