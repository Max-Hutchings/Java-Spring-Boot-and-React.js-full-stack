import axios from "axios";


export default async function usePostPeepComment(peepId, commentText){
    try{
        const response = await axios.post("http://localhost:4000/peep-comment/add-peep-comment",
            {
                "peepId": peepId,
                "commentText": commentText,
            },
            {
                withCredentials: true
            })
        return response.data;
    }catch(e){
        console.log("Failed to add peep");
        console.log(e);
    }
}