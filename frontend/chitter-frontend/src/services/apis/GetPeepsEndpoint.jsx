import axios from "axios";


export default async function callGetAllPeeps(){
    try{
        const response = await axios.get("http://localhost:4000/post/all-peeps");
        console.log(response);
        return response.data;
    }catch(e){
        console.log("Couldn't get peeps");
        console.log(e);
    }
}