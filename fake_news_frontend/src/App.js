import React, { useState, useEffect } from 'react';
import AuthService from "./AuthService";
import './App.css';

function App() {
  const [currentUser, setCurrentUser] = useState(undefined);
  const [text, setText] = useState('');
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [history, setHistory] = useState([]);
  const [loginData, setLoginData] = useState({
    username: '',
    password: ''
  });
  const [registerData, setRegisterData] = useState({
    username: '',
    password: ''
  });
  const [isLoginMode, setIsLoginMode] = useState(true);

  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if (user) {
      setCurrentUser(user);
      fetchHistory(user.userId);
    }
  }, []);

  const fetchHistory = async (userId) => {
    try {
      const response = await fetch(`/api/predictions/user?userId=${userId}`);
      if (response.ok) {
        const data = await response.json();
        setHistory(data);
      } else if (response.status === 204) {
        setHistory([]); // No content
      } else {
        console.error('Failed to fetch history:', response.status);
      }
    } catch (err) {
      console.error('Error fetching history:', err);
    }
  };

  const handleLogin = (e) => {
    e.preventDefault();
    AuthService.login(loginData.username, loginData.password)
        .then(() => {
          const user = AuthService.getCurrentUser();
          setCurrentUser(user);
          fetchHistory(user.userId);
        })
        .catch(err => alert(err.message));
  };

  const handleRegister = (e) => {
    e.preventDefault();
    AuthService.register(registerData.username, registerData.password)
        .then(() => {
          alert('Registration successful! Please login.');
          setRegisterData({ username: '', password: '' });
          setIsLoginMode(true);
        })
        .catch(err => alert(err.message));
  };

  const handleLogout = () => {
    AuthService.logout();
    setCurrentUser(undefined);
    setHistory([]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!currentUser) return;

    setLoading(true);
    try {
      console.log('Current user:', currentUser);
      console.log('Sending request with userId:', currentUser.userId);

      const requestBody = {
        text: text,
        username: currentUser.username
      };

      const requestHeaders = {
        'Content-Type': 'application/json',
        'userId': currentUser.userId.toString()
      };

      console.log('Request body:', requestBody);
      console.log('Request headers:', requestHeaders);
      console.log('Request URL:', '/api/predictions');

      const res = await fetch('/api/predictions', {
        method: 'POST',
        headers: requestHeaders,
        body: JSON.stringify(requestBody)
      });

      console.log('Response status:', res.status);
      console.log('Response headers:', [...res.headers.entries()]);

      if (!res.ok) {
        const errorText = await res.text();
        console.error('Error response:', errorText);
        throw new Error(`HTTP ${res.status}: ${errorText}`);
      }

      const data = await res.json();
      console.log('Response data:', data);

      setResult(data);
      setText('');
      await fetchHistory(currentUser.userId);

    } catch (err) {
      console.error('Prediction error:', err);
      alert("Error: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  if (!currentUser) {
    return (
        <div className="auth-container">
          <div className="auth-form">
            <div className="auth-tabs">
              <button
                  className={isLoginMode ? 'active' : ''}
                  onClick={() => setIsLoginMode(true)}
              >
                Login
              </button>
              <button
                  className={!isLoginMode ? 'active' : ''}
                  onClick={() => setIsLoginMode(false)}
              >
                Register
              </button>
            </div>

            {isLoginMode ? (
                <form onSubmit={handleLogin}>
                  <h2>Login</h2>
                  <input
                      type="text"
                      placeholder="Username"
                      value={loginData.username}
                      onChange={(e) => setLoginData({...loginData, username: e.target.value})}
                      required
                  />
                  <input
                      type="password"
                      placeholder="Password"
                      value={loginData.password}
                      onChange={(e) => setLoginData({...loginData, password: e.target.value})}
                      required
                  />
                  <button type="submit">Login</button>
                </form>
            ) : (
                <form onSubmit={handleRegister}>
                  <h2>Register</h2>
                  <input
                      type="text"
                      placeholder="Username"
                      value={registerData.username}
                      onChange={(e) => setRegisterData({...registerData, username: e.target.value})}
                      required
                  />
                  <input
                      type="password"
                      placeholder="Password"
                      value={registerData.password}
                      onChange={(e) => setRegisterData({...registerData, password: e.target.value})}
                      required
                  />
                  <button type="submit">Register</button>
                </form>
            )}
          </div>
        </div>
    );
  }

  return (
      <div className="App">
        <header>
          <h1>Fake News Detection</h1>
          <div className="user-info">
            <span>Welcome, {currentUser.username}</span>
            <button onClick={handleLogout}>Logout</button>
          </div>
        </header>

        <div className="content">
          <div className="prediction-form">
            <form onSubmit={handleSubmit}>
            <textarea
                placeholder="Paste the news text here..."
                value={text}
                onChange={(e) => setText(e.target.value)}
                required
            />
              <button type="submit" disabled={loading}>
                {loading ? "Analyzing..." : "Analyze"}
              </button>
            </form>

            {result && (
                <div className="result">
                  <h2>Prediction Result</h2>
                  <p><strong>Label:</strong> {result.label}</p>
                  <p><strong>Confidence:</strong> {(result.confidence *100).toFixed(2)}%</p>
                </div>
            )}
          </div>

          <div className="history">
            <h2>Your Previous Searches</h2>
            {history.length > 0 ? (
                <ul>
                  {history.map((item, index) => (
                      <li key={index}>
                        <p><strong>Text:</strong> {item.text.substring(0, 100)}...</p>
                        <p><strong>Result:</strong> {item.response?.label} (Confidence: {(item.response?.confidence* 100 ).toFixed(2)}%)</p>
                      </li>
                  ))}
                </ul>
            ) : (
                <p>No previous searches found</p>
            )}
          </div>
        </div>
      </div>
  );
}

export default App;