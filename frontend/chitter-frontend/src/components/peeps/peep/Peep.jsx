import {Link} from "react-router-dom";
import {useState} from "react";
import PeepReply from "../peep_comments/PeepReply.jsx";
import CommentsSection from "../peep_comments/CommentsSection.jsx";
import {useAuth} from "../../../services/context/AuthContext.jsx";




export default function Peep({username, textContent, peepId}){

    const {loggedIn} = useAuth();

    const [openReply, setOpenReply] = useState(false);
    const [openComments, setOpenComments] = useState(false);
    const [addedComment, setAddedComment] = useState(0);

    async function handleReplyBtn(){
        await setOpenReply(!openReply);
        // If reply open but comments closed, dont re-open comments.
        if (openReply && !openComments) setOpenReply(!openReply);
        // If comments open and reply closed, dont close comments.
        if (openComments && !openReply) setOpenReply(!openReply);
        // If both open, close both.// If both closed, open both
        if (openReply === openComments){
            setOpenComments(!openComments);
            setOpenReply(!openReply);
        }
    }

    return(
        <>

                <div className="d-flex flex-column align-items-start justify-content-start p-3 m-2 rounded mb-3 shadow" style={{background: "white"}}>
                    <h6 role={"peep-username"}>
                        {username}
                    </h6>
                    <p role={"peep-content"}>{textContent}</p>
                    <div className="row justify-content-center w-100">
                        <div className="col-6 justify-content-center text-center">
                            <Link onClick={(e) => setOpenComments(!openComments)} to={"#"} role={"open-comments-btn"} className={"text-center text-decoration-none"}>Comments</Link>
                        </div>
                        {loggedIn &&
                            <div className="col-6 justify-content-center text-center ">
                                <Link onClick={(e) => handleReplyBtn()} to={"#"} role={"reply-btn"} className={"text-center text-decoration-none"}>Reply</Link>
                            </div>
                        }
                    </div>
                    {openReply &&
                        <div className="row w-100">
                            <div className="col-12">
                                <PeepReply setOpenReply={setOpenReply} peepId={peepId} addedComment={addedComment} setAddedComment={setAddedComment}/>
                            </div>
                        </div>
                    }

                    {openComments &&
                        <div className="row w-100">
                            <div className="col-12">
                                <CommentsSection peepId={peepId} addedComment={addedComment}/>
                            </div>
                        </div>
                    }
                </div>


        </>
    )
}