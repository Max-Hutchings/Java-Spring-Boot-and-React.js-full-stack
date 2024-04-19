import axios from "axios";


export default async function callLogoutEndpoint(){
    try{
        const response = await axios.post(
            "http://localhost:4000/authentication/logout");
        return response;
    }catch(e){
        console.log(`Login error: ` + e);
        return { error: e.response?.data || "Unknown error"};
    }
}