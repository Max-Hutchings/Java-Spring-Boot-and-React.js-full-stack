

export const getAllPeepsData = {
    data: [
        {
            "textContent": "This is my peep",
            "createdDate": "2024-03-19T15:40:00.000Z",
            "accountId": {
                "username": "User 1",
            },
        },
        {
            "textContent": "another peep",
            "createdDate": "2024-03-19T15:30:00.000Z",
            "accountId": {
                "username": "User 2",
            },
        }
    ]
}


export const getAllCommentsData = {
    message: "Successfully gathered peeps",
    data:[
        {
            _id: '65faeadd0b5a89f50edb2102', // Mock comment ID
            peepId: '65f9cfd3cbf01496e1', // Mock peep ID associated with the comment
            accountId: {
                username: "sdffsdfsdfsd",
                _id: "65f8dskfnolsdfgv"
            }, // Account information placeholder
            commentText: 'this is my forth', // Mock comment text
            createdDate: '2024-03-20T13:55:41.680Z' // Mock creation date of the comment
        },
        {
            _id: '65faeaa10b5a89f50edb20f8', // Mock comment ID
            peepId: '65f9cfd3cbf01496e1', // Mock peep ID associated with the comment
            accountId: {
                username: "sdffsdfsdfsd",
                _id: "65f86gdsnfogss"
            }, // Account information placeholder
            commentText: 'this is my third', // Mock comment text
            createdDate: '2024-03-20T13:54:41.761Z' // Mock creation date of the comment
        },
        {
            _id: '65faea2b0b5a89f50edb20ec', // Mock comment ID
            peepId: '65f9cfd3cbf01496e1', // Mock peep ID associated with the comment
            accountId: {
                username: "sdffsdfsdfsd",
                _id: "65f86agofpsmgsis"
            }, // Account information placeholder
            commentText: 'This is my second comment', // Mock comment text
            createdDate: '2024-03-20T13:52:43.411Z' // Mock creation date of the comment
        },
        {
            _id: '65faea250b5a89f50edb20e8', // Mock comment ID
            peepId: '65f9cfd3cbf01496e1', // Mock peep ID associated with the comment
            accountId: {
                username: "sdffsdfsdfsd",
                _id: "65f86afdhosgfsdfsfd98"
            }, // Account information placeholder
            commentText: 'This is my first comment', // Mock comment text
            createdDate: '2024-03-20T13:52:37.428Z' // Mock creation date of the comment
        }
    ]
}


export const getComments = {
    "message": "Successfully gathered comments for peep 65f9cfd3cbfed5bcc01496e1",
    "data": [
    {
        "_id": "65faeadd0b5a89f50edb2102",
        "peepId": "65f9cfd3cbfed5bcc01496e1",
        "accountId": {
            "_id": "65f86ae54bcfd83db19e7f98",
            "username": "David"
        },
        "commentText": "this is my forth",
        "createdDate": "2024-03-20T13:55:41.680Z",
        "__v": 0
    },
    {
        "_id": "65faeaa10b5a89f50edb20f8",
        "peepId": "65f9cfd3cbfed5bcc01496e1",
        "accountId": {
            "_id": "65f86ae54bcfd83db19e7f98",
            "username": "Tim"
        },
        "commentText": "this is my third",
        "createdDate": "2024-03-20T13:54:41.761Z",
        "__v": 0
    },
    {
        "_id": "65faea2b0b5a89f50edb20ec",
        "peepId": "65f9cfd3cbfed5bcc01496e1",
        "accountId": {
            "_id": "65f86ae54bcfd83db19e7f98",
            "username": "Lenka"
        },
        "commentText": "This is my second comment",
        "createdDate": "2024-03-20T13:52:43.411Z",
        "__v": 0
    },
    {
        "_id": "65faea250b5a89f50edb20e8",
        "peepId": "65f9cfd3cbfed5bcc01496e1",
        "accountId": {
            "_id": "65f86ae54bcfd83db19e7f98",
            "username": "Carry"
        },
        "commentText": "This is my first comment",
        "createdDate": "2024-03-20T13:52:37.248Z",
        "__v": 0
    }
]
}