import PeepSection from "../components/peeps/PeepSection.jsx";
import Navbar from "../components/navigation/Narbar.jsx";
import Footer from "../components/navigation/Footer.jsx";

export default function Home(){

    return(
        <>
            <div className="d-flex flex-column justify-content-between" style={{height: "100vh"}}>
                <Navbar />
                <div className="container-fluid mt-2 mb-2">
                    <PeepSection />
                </div>

                <Footer />
            </div>
        </>
    )
}