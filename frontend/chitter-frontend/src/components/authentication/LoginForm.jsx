import {useState} from "react";
import callLoginEndpoint from "../../services/apis/LoginEndpoint.jsx";

import { useAuth } from "../../services/context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";


export default function LoginForm(){

    const { handleLogin } = useAuth();

    const navigate = useNavigate();

    const [login, setLogin] = useState(
        {
            "email": "",
            "password": "",
        }
    )

    async function submitLogin(){
        try{
            const userDetails = await callLoginEndpoint(login);
            if (userDetails.status !== 200) throw new Error();
            await handleLogin(userDetails.data);
            navigate("/");
        } catch (e){
            console.error("Login error:", e);
            alert("Failed to Login. Please try again.");
        }
    }

    return(
        <>
            <div className="container">
                <form onSubmit={(e) => {e.preventDefault(); submitLogin();}}>
                    <input
                        type="email"
                        className="form-control m-2"
                        value={login.email}
                        placeholder={"Email"}
                        onChange={(e) => setLogin({...login, email: e.target.value})}
                    />

                    <input
                        type="password"
                        className="form-control m-2"
                        value={login.password}
                        placeholder={"Password"}
                        onChange={(e) => setLogin({...login, password: e.target.value})}
                    />
                    <button className="btn btn-primary text-light w-100 m-2">
                        Login
                    </button>
                </form>
            </div>
        </>
    )
}