import {Route, Routes, useNavigate} from "react-router-dom";
import Signup from "./pages/Signup.jsx";
import Login from "./pages/Login.jsx";
import Home from "./pages/Home.jsx";

import {AuthProvider, useAuth} from "./services/context/AuthContext.jsx";
import {useEffect} from "react";
import callLoginEndpoint from "./services/apis/LoginEndpoint.jsx";
import callValidateJWT from "./services/apis/ValidateJWTEndpoint.jsx";


function App() {

    const { handleLogin, handleLogout, loggedIn } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const getUser = async() => {
            const userDetails = await callValidateJWT();
            // console.log(userDetails)
            if (userDetails?.status === 200){
                await handleLogin(userDetails.data);
                navigate("/");
            }else{
                if (loggedIn) {
                    await handleLogout();
                }
            }
        }
        getUser();
    }, []);


  return (
    <>
          <Routes>
              <Route path={"/signup"} element={<Signup />} />
              <Route path={"/login"} element={<Login />} />
              <Route path={"/"} element={<Home />} />
           </Routes>
    </>
  )
}

export default App
