import SignupForm from "../components/authentication/SignupForm.jsx";
import Navbar from "../components/navigation/Narbar.jsx";
import Footer from "../components/navigation/Footer.jsx";


export default function Signup(){


    return(
        <>
            <div className="d-flex flex-column justify-content-between" style={{height: "100vh"}}>
                <Navbar />
                <div className="container">
                    <div className="row justify-content-center align-items-center">
                        <div className="col-lg-4 col-12">
                <SignupForm />
                        </div>
                    </div>
                </div>
                <Footer />
            </div>
        </>
    )
}