import callGetAllPeeps from "../../src/services/apis/GetPeepsEndpoint.jsx"; // Update with the correct path
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

const mock = new MockAdapter(axios);

describe('callGetAllPeeps', () => {
    it('fetches successfully data from an API', async () => {
        const data = {
            peeps: [{ id: 1, name: 'Jimmy' }] // Example response data
        };

        mock.onGet("http://localhost:4000/peep/all-peeps").reply(200, data);

        await expect(callGetAllPeeps()).resolves.toEqual(data);
    });

});
