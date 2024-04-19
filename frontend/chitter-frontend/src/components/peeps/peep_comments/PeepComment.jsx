


export default function PeepComment({commentUser, commentText}){

    return(
        <>
            <div role={"peep-comment"} className={"border rounded p-2 mb-1"}>
                <div className="row">
                    <div className="col-6" >
                        <span className={"small"} style={{fontWeight: "bold"}}>
                            {commentUser}
                        </span>

                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <span className="small">
                            {commentText}
                        </span>
                    </div>
                </div>
            </div>

        </>
    )
}