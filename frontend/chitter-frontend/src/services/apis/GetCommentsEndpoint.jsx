import axios from "axios";


export default async function callGetComments({peepId}){
    try{
        const response = await axios.get(
            `http://localhost:4000/post-comment/peep-comments/${peepId}`,
            {
                withCredentials: true
            }
            );
        return response.data;
    }catch(e){
        console.log("Failed to get comments");
        console.log(e);
    }
}