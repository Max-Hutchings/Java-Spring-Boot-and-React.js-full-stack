import {useState} from "react";
import callAddPeep from "../../../services/apis/PostPeepEndpoint.jsx";


export default function AddPeep({closeModal, setAddedPeep, addedPeep}){

    const modalStyle = {
        position: 'fixed',     // Fixed position to overlay on the screen
        top: '50%',            // Centered vertically
        left: '50%',           // Centered horizontally
        transform: 'translate(-50%, -50%)', // Adjust to center the modal
        zIndex: 1000,          // Ensure it's on top of other elements
        backgroundColor: '#FFF', // Background color of the modal
        width: '50%',          // Modal width
        maxWidth: '600px',     // Maximum width of the modal
    };

    const backdropStyle = {
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(0, 0, 0, 0.7)', // Semi-transparent black
        zIndex: 1000 // Same zIndex to ensure it's under the modal
    };

    const [peepForm, setPeepForm] = useState("");

    async function handlePostPeep(){
        try{
            const response = await callAddPeep({textContent: peepForm});
            if (response.status !== 200) throw new Error();
            setAddedPeep(addedPeep += 1);
            closeModal();
        }catch (e){
            alert("Failed to post peep");
            console.log(e);
        }
    }

    return(
        <>
        <div style={backdropStyle}></div>
            <div role={"create-peep-modal"} className="container rounded shadow-lg" style={modalStyle}>
                <form onSubmit={(e) => {e.preventDefault(); handlePostPeep();}}>
                    <div className="d-flex justify-content-between align-items-center">
                        <h2 className={"m-2"}>Post Peep</h2>
                        <svg style={{cursor: "pointer"}} onClick={closeModal} xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                             className="bi bi-x-lg" viewBox="0 0 16 16">
                            <path
                                d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8z"/>
                        </svg>
                    </div>
                    <textarea
                        className={"form-control mt-2 mb-2 ms-1 me-1"}
                        rows={3}
                        value={peepForm}
                        placeholder={"Peep away..."}
                        onChange={(e) => {
                            setPeepForm(e.target.value)
                        }}
                    />
                    <button className="btn btn-primary text-color-light m-2">
                        Post peep
                    </button>
                </form>
            </div>
        </>
    )
}