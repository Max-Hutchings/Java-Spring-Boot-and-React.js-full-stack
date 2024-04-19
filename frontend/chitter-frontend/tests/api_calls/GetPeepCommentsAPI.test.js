import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import callGetComments from "../../src/services/apis/GetCommentsEndpoint.jsx";

describe('callGetComments', () => {
    it('fetches comments successfully', async () => {
        const mock = new MockAdapter(axios);
        const data = ["comment1", "comment2", "comment3"];
        mock.onGet(`http://localhost:4000/peep-comment/1`).reply(200, data);

        const response = await callGetComments({ peepId: 1 });
        expect(response).toEqual(data);
    });
});