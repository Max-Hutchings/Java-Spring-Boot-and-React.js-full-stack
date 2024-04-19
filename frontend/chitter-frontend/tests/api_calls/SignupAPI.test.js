import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import callSignupEndpoint from "../../src/services/apis/SignupEndpoint.jsx";
import {expect} from "vitest";

describe('callSignupEndpoint', () => {
    const mock = new MockAdapter(axios);
    const userData = {
        fName: 'Jimmy',
        lName: 'Kimmle',
        username: 'jkBoy',
        email: 'jk@example.com',
        password: 'password123FDF###'
    };

    it('should return data on successful signup', async () => {
        const responseData = { message: 'Successfully create account' };
        mock.onPost("http://localhost:4000/authentication/sign-up").reply(200, responseData);

        const result = await callSignupEndpoint(userData);
        expect(result).toEqual(responseData);
    });

});
