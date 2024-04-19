import {useState} from "react";
import callSignupEndpoint from "../../services/apis/SignupEndpoint.jsx";
import {useNavigate} from "react-router-dom";


export default function SignupForm(){

    const navigate = useNavigate();

    const [signup, setSignup] = useState(
        {
            "fName": "",
            "lName": "",
            "username": "",
            "email": "",
            "password": ""
        }
    );

    async function submitSignup(){
        try{
            await callSignupEndpoint(signup);
            navigate("/login");
        } catch (e){
            console.error("Signup error:", e);
            alert("Failed to sign up. Please try again.");
        }
    }


    return(
        <>
            <div className="container">
                <form onSubmit={(e) => {
                    e.preventDefault();
                    submitSignup();
                }}>
                    <div className="row m-2 p-0 d-flex justify-content-between w-100">
                        <div className="col-5 p-0">
                            <input
                                className={"form-control w-100"}
                                type="text"
                                value={signup.fName}
                                placeholder={"First Name"}
                                onChange={(e) => setSignup({...signup, fName: e.target.value})}
                            />
                        </div>
                        <div className="col-6 p-0">
                            <input
                                className={"form-control w-100"}
                                type="text"
                                value={signup.lName}
                                placeholder={"Last Name"}
                                onChange={(e) => {
                                    setSignup({...signup, lName: e.target.value})
                                }}
                            />
                        </div>

                    </div>


                    <input
                        className={"form-control m-2"}
                        type="text"
                        value={signup.username}
                        placeholder={"Username"}
                        onChange={(e) => {
                            setSignup({...signup, username: e.target.value})
                        }}
                    />

                    <input
                        className={"form-control m-2"}
                        type="email"
                        value={signup.email}
                        placeholder={"Email"}
                        onChange={(e) => setSignup({...signup, email: e.target.value})}
                    />

                    <input
                        className={"form-control m-2"}
                        type="password"
                        value={signup.password}
                        placeholder={"Password"}
                        onChange={(e) => setSignup({...signup, password: e.target.value})}
                    />

                    <button className="btn btn-primary text-light w-100 m-2">
                        Sign Up
                    </button>

                </form>
            </div>

        </>
    )
}