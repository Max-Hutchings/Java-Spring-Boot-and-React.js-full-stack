import Peep from "./peep/Peep.jsx";
import {useEffect, useState} from "react";
import AddPeep from "./peep/AddPeep.jsx";
import callGetAllPeeps from "../../services/apis/GetPeepsEndpoint.jsx";
import NewPeepBtn from "./peep/NewPeepBtn.jsx";
import {useAuth} from "../../services/context/AuthContext.jsx";


export default function PeepSection(){

    const {loggedIn} = useAuth();
    const [createPeepModal, setCreatePeepModal] = useState(false);
    const [peeps, setPeeps] = useState([]);
    const [loading, setLoading] = useState(true);
    const [addedPeep, setAddedPeep] = useState(0);



    useEffect(() => {
        const fetchPeeps = async () => {
            const data = await callGetAllPeeps();
            const sortedPeeps = data.sort((a, b) => {
                return new Date(b.createdDate) - new Date(a.createdDate);
            });
            setPeeps(sortedPeeps);
            setLoading(false);
        };

        fetchPeeps();
    }, [addedPeep]);




    return(
        <>
            <div role={"peep-feed"} className="container" >
                {createPeepModal && <AddPeep closeModal={() => setCreatePeepModal(false)} setAddedPeep={setAddedPeep} addedPeep={addedPeep}/>}
                <div className="row justify-content-center">
                    <div className="col-10 col-lg-7  justify-content-center align-items-center"
                         >
                        {peeps.map((peep, index) => {
                            return(
                                <Peep key={index} peepId={peep.id} textContent={peep.postContent} username={peep.accountUsername}/>
                            )
                        })}

                    </div>
                </div>
                {loggedIn && <NewPeepBtn setCreatePeep={setCreatePeepModal} />}
            </div>

        </>
    )
}