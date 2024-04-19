import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import callAddPeep from "../../src/services/apis/PostPeepEndpoint.jsx";

describe('callAddPeep', () => {
    it('posts peep and returns response', async () => {
        const mock = new MockAdapter(axios);
        const data = { textContent: 'test peep' };
        const response = { data: 'Peep added' };

        mock.onPost('http://localhost:4000/peep/add-peep', data).reply(200, response);

        const result = await callAddPeep(data);
        expect(result.data).toEqual(response);
    });

});