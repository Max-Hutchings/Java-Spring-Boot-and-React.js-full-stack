import axios from "axios";


export default async function callSignupEndpoint({fName, lName, username, email, password}){
    try{
        const response = await axios.post(
            "http://localhost:4000/authentication/sign-up",
            {
                "fName": fName,
                "lName": lName,
                "username": username,
                "email": email,
                "password": password
            });
        return response.data;
    }catch(e){
        console.log(`Signup error: ` + e);
        return { error: e.response?.data || "Unknown error"};
    }
}