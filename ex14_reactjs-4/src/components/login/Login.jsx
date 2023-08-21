import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthProvider';

export const Login = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [usernameError, setUsernameError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  // const [isLoggedIn, setIsLoggedIn] = useState(false);
  const { handleLogin } = useAuth();

  const handleLogins = (e) => {
    e.preventDefault();
    setUsernameError();
    setPasswordError();
    setErrorMessage();

    if (username === '') {
      setUsernameError('Please enter your username');
      return;
    }

    if (password === '') {
      setPasswordError('Please enter your password');
      return;
    }

    if (username === 'admin' && password === 'admin') {
      handleLogin();
      navigate('/categories');
    } else {
      setErrorMessage('Username/password is incorrect');
    }
  };

  return (
    <div>
      <form action="" method="get">
        <h2>Login</h2>
        <div>
          <label htmlFor="">Username: </label>
          <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
          {usernameError && <p style={{ color: 'red' }}>{usernameError}</p>}
        </div>
        <div>
          <label htmlFor="">Password: </label>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {passwordError && <p style={{ color: 'red' }}>{passwordError}</p>}
        </div>

        {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        <button onClick={handleLogins}>Đăng nhập</button>
      </form>
    </div>
  );
};
