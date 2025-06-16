const API_URL = 'http://localhost:8080/api/auth';

const handleResponse = async (response) => {
    if (!response.ok) {
        try {
            const error = await response.json();
            throw new Error(error.message || 'Something went wrong');
        } catch {
            const errorText = await response.text();
            throw new Error(errorText || 'Something went wrong');
        }
    }

    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
        return response.json();
    } else {
        return response.text();
    }
};

export const register = async (username, password) => {
    const response = await fetch(`${API_URL}/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });
    return handleResponse(response);
};

export const login = async (username, password) => {
    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });
    const data = await handleResponse(response);

    if (typeof data === 'object' && data.userId) {
        localStorage.setItem('user', JSON.stringify({
            username,
            userId: data.userId
        }));
    }
    return data;
};

export const logout = () => {
    localStorage.removeItem('user');
};

export const getCurrentUser = () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
};

const authService = { register, login, logout, getCurrentUser };
export default authService;