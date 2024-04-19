import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import callLoginEndpoint from "../../src/services/apis/LoginEndpoint.jsx";

describe('callLoginEndpoint', () => {
    const mock = new MockAdapter(axios);
    const loginData = { email: 'test@example.com', password: 'password123' };
    const mockResponse = {
        message: 'Login Successful',
        fName: 'John',
        lName: 'Doe',
        email: 'test@example.com'
    };

    it('should return the correct response on successful login', async () => {
        mock.onPost("http://localhost:4000/authentication/login").reply(200, mockResponse);

        const result = await callLoginEndpoint(loginData);
        expect(result.data).toEqual(mockResponse);
    });

});