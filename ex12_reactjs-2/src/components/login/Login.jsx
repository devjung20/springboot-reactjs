import React, { useState } from 'react';
import Dashboard from '../layout/dashboard/Dashboard';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [usernameError, setUsernameError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogin = (event) => {
    event.preventDefault();
    setUsernameError('');
    setPasswordError('');
    setErrorMessage('');

    if (username === '') {
      setUsernameError('Vui lòng nhập tên đăng nhập');
      return;
    }

    if (password === '') {
      setPasswordError('Vui lòng nhập mật khẩu');
      return;
    }

    if (username === 'admin' && password === 'admin') {
      setIsLoggedIn(true);
    } else {
      setErrorMessage('Username/password không đúng');
    }
  };

  if (isLoggedIn) {
    return <Dashboard username={username} />;
  }

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
        <button onClick={handleLogin}>Đăng nhập</button>
      </form>
    </div>
  );
};

export default Login;
