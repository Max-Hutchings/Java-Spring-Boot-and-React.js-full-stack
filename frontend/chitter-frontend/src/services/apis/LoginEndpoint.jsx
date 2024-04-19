import axios from "axios";

axios.defaults.withCredentials = true;

export default async function callLoginEndpoint({email, password}){
    try{
        const response = await axios.post(
            "http://localhost:4000/authentication/login",
            {
                "email": email,
                "password": password,
            },
            {
                withCredentials: true
            }
            );
        // console.log(response);
        return response;
    }catch(e){
        console.log(`Login error: ` + e);
        return { error: e.response?.data || "Unknown error"};
    }
}