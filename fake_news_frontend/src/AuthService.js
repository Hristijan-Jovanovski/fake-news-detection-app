const API_URL = 'http://localhost:8080/api/auth';

const handleResponse = async (response) => {
    console.log('Response status:', response.status);
    console.log('Response headers:', response.headers);
    console.log('Response ok:', response.ok);

    if (!response.ok) {
        let errorMessage = `HTTP ${response.status}: ${response.statusText}`;

        try {
            const contentType = response.headers.get('content-type');
            console.log('Error response content-type:', contentType);

            if (contentType && contentType.includes('application/json')) {
                const error = await response.json();
                console.log('Error response JSON:', error);
                errorMessage = error.message || errorMessage;
            } else {
                const errorText = await response.text();
                console.log('Error response text:', errorText);
                errorMessage = errorText || errorMessage;
            }
        } catch (parseError) {
            console.error('Error parsing error response:', parseError);
        }

        throw new Error(errorMessage);
    }

    // Handle successful response
    const contentType = response.headers.get('content-type');
    console.log('Success response content-type:', contentType);

    if (contentType && contentType.includes('application/json')) {
        const jsonData = await response.json();
        console.log('Success response JSON:', jsonData);
        return jsonData;
    } else {
        const textData = await response.text();
        console.log('Success response text:', textData);
        return textData;
    }
};

export const register = async (username, password) => {
    console.log('Registering user:', username);
    const response = await fetch(`/api/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });
    return handleResponse(response);
};

export const login = async (username, password) => {
    console.log('Logging in user:', username);
    const response = await fetch(`/api/auth/login`, {
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