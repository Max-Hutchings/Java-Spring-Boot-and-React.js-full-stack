import {Link, useNavigate} from "react-router-dom";
import callLogoutEndpoint from "../../services/apis/LogoutEndpoint.jsx";
import {useAuth} from "../../services/context/AuthContext.jsx";

export default function Navbar(){

    const {handleLogout, loggedIn} = useAuth();
    const navigate = useNavigate();

    async function logout(){
        try {
            const result = await callLogoutEndpoint();
            if (result.status === 200) {
                await handleLogout();
                navigate("/login");
            }
        } catch (e){
            console.error("Login error:", e);
            alert("Failed to Login. Please try again.");
        }
    }

    return(
        <>
            <nav className="navbar navbar-expand-md bg-primary navbar-dark" >
                <div className="container">
                    <Link to={"/"} className="navbar-brand">
                        <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="white"
                             className="bi bi-twitter" viewBox="0 0 16 16">
                            <path
                                d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334q.002-.211-.006-.422A6.7 6.7 0 0 0 16 3.542a6.7 6.7 0 0 1-1.889.518 3.3 3.3 0 0 0 1.447-1.817 6.5 6.5 0 0 1-2.087.793A3.286 3.286 0 0 0 7.875 6.03a9.32 9.32 0 0 1-6.767-3.429 3.29 3.29 0 0 0 1.018 4.382A3.3 3.3 0 0 1 .64 6.575v.045a3.29 3.29 0 0 0 2.632 3.218 3.2 3.2 0 0 1-.865.115 3 3 0 0 1-.614-.057 3.28 3.28 0 0 0 3.067 2.277A6.6 6.6 0 0 1 .78 13.58a6 6 0 0 1-.78-.045A9.34 9.34 0 0 0 5.026 15"/>
                        </svg>
                    </Link>

                    <Link to={"/"} className={"text-decoration-none"}>
                        <h1 style={{color: "white"}}>Chitter</h1>
                    </Link>
                    <button
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarNav"
                        className="navbar-toggler"
                    >
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id={"navbarNav"}>
                        <ul className="navbar-nav ms-auto">
                            {loggedIn ? (
                                <li onClick={logout} className="nav-item nav-link text-decoration-none"
                                    style={{cursor: "pointer", color: "white", fontSize: "1.3rem"}}>
                                    Logout
                                </li>
                            ) : (
                                <li className="nav-item nav-link text-decoration-none"
                                    style={{cursor: "pointer", color: "white", fontSize: "1.3rem"}}>
                                    <Link to={"/login"} className="text-white text-decoration-none">
                                        Login
                                    </Link>
                                </li>
                            )}
                            {!loggedIn &&
                                <li className="nav-item nav-link text-decoration-none"
                                    style={{cursor: "pointer", color: "white", fontSize: "1.3rem"}}>
                                    <Link to={"/signup"} className="text-white text-decoration-none">
                                        Sign up
                                    </Link>
                                </li>
                            }
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )

}