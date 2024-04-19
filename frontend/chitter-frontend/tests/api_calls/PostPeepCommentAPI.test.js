import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import usePostPeepComment from "../../src/services/apis/PostPeepCommentEndpoint.jsx";

describe('usePostPeepComment', () => {
    it('posts comment successfully', async () => {
        const mock = new MockAdapter(axios);
        const data = { response: true };
        mock.onPost(`http://localhost:4000/peep-comment/add-peep-comment`).reply(200, data);

        const response = await usePostPeepComment({ peepId: 1, commentText: 'Test comment' });
        expect(response).toEqual(data);
    });
});