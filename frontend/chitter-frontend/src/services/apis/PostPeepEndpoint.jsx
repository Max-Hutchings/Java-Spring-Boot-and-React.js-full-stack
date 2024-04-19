import axios from "axios";

// axios.defaults.withCredentials = true;

export default async function callAddPeep({textContent}){
    try{
        return await axios.post("http://localhost:4000/post/add-peep",
            {"postContent": textContent},
            {
                withCredentials: true
            });
    }catch(e){
        console.log("Couldn't post peep");
        console.log(e);
    }
}