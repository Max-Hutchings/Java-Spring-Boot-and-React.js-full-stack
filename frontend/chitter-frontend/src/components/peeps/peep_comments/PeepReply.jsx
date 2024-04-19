import {useState} from "react";
import usePostPeepComment from "../../../services/apis/PostPeepCommentEndpoint.jsx";


export default function PeepReply({peepId, addedComment, setAddedComment, setOpenReply}){

    const [replyText, setReplyText] = useState("");

    async function handlePostPeepCommentAPI() {
        await usePostPeepComment(peepId, replyText);
        setAddedComment(addedComment += 1);
        setReplyText("");
        setOpenReply(false);
    }

    return(
        <>
            <form role={"comment-form"} onSubmit={(e)=>{e.preventDefault(); handlePostPeepCommentAPI()}}>
                <div className="row w-100 m-2  rounded p-1">
                    <div className="col-11">
                        <input
                            type="text"
                            role={"peep-reply-input-box"}
                            className="form-control form-control-sm"
                            value={replyText}
                            placeholder={"Reply..."}
                            onChange={(e) => {
                                setReplyText(e.target.value)
                            }}
                        />
                    </div>
                    <div className="col-1 d-flex justify-content-center">
                        <button  className="btn btn-primary btn-sm text-light justify-content-center">
                            Reply
                        </button>
                    </div>
                </div>


            </form>
        </>
    )
}