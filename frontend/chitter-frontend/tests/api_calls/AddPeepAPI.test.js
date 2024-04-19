import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import callAddPeep from "../../src/services/apis/PostPeepEndpoint.jsx";

describe('callAddPeep', () => {
    it('posts peep and returns response', async () => {
        const mock = new MockAdapter(axios);
        const requestPayload = { postContent: 'test peep' };
        const response = { data: 'Peep added' };

        mock.onPost('http://localhost:4000/post/add-peep', requestPayload).reply(200, response);

        const dataToSend = { textContent: 'test peep' };
        const result = await callAddPeep(dataToSend);
        expect(result.data).toEqual(response);
    });
});
