import {MemoryRouter} from "react-router-dom";
import {AuthProvider} from "../../src/services/context/AuthContext.jsx";
import { act, fireEvent, render, screen, waitFor} from "@testing-library/react";
import callGetAllPeeps from "../../src/services/apis/GetPeepsEndpoint.jsx";
import callGetComments from "../../src/services/apis/GetCommentsEndpoint.jsx";
import {getAllCommentsData, getAllPeepsData, getComments} from "./mock_data/HomeMockData.jsx";
import RenderHomeWithLoggedIn from "./mock_data/settingAuthContext.jsx";


vi.mock("../../src/services/apis/GetPeepsEndpoint.jsx");
vi.mock("../../src/services/apis/GetCommentsEndpoint.jsx");






describe("Testing Home page", () => {

    beforeEach(async() => {
        callGetAllPeeps.mockResolvedValue(getAllPeepsData);
        callGetComments.mockResolvedValue(getComments);
        await act(async() => {
            render(
                <MemoryRouter>
                    <AuthProvider>
                        <RenderHomeWithLoggedIn />
                    </AuthProvider>
                </MemoryRouter>
            )
        })
        }
    )


    describe("Testing nav and footer", () => {
        it("Should render the navbar", () => {

            const navbarElement = screen.getByRole("navigation");
            expect(navbarElement).toBeInTheDocument()
        });

        it("Should render the footer", () => {
            const footerElement = screen.getByRole("footer");
            expect(footerElement).toBeInTheDocument();
        })
    })

    describe("Testing peeps feed", () => {
        it("Should render the peeps-feed on page", () => {
            const peepFeed = screen.getByRole("peep-feed");
            expect(peepFeed).toBeInTheDocument();

        })

        it("Should render the add peep btn", () => {
            const addPeepBtn = screen.getByRole("add-peep-btn");
            expect(addPeepBtn).toBeInTheDocument();
        })

        it("Clicking addPeepBtn should set createPeepModal to true", async() =>{
            const addPeepBtn = screen.getByRole("add-peep-btn");
             await act(() => {
                fireEvent.click(addPeepBtn);
            })


            const createPeepModal = screen.getByRole("create-peep-modal");
            expect(createPeepModal).toBeInTheDocument();
        })


        it("Should render 2 comment btn", () => {
            const commentsBtn = screen.getAllByRole('open-comments-btn');
            expect(commentsBtn).toHaveLength(2);
        });

        it("Should render 2 reply btn", () => {
            const replyBtn = screen.getAllByRole('reply-btn');
            expect(replyBtn).toHaveLength(2);
        });

        it("First peep should have correct username and content", () => {
            const usernameElements = screen.getAllByRole('peep-username');
            const contentElements = screen.getAllByRole('peep-content');

            expect(usernameElements[0]).toHaveTextContent('User 1');
            expect(contentElements[0]).toHaveTextContent('This is my peep');
        });

        it("Second peep should have correct username and content", () => {
            const usernameElements = screen.getAllByRole('peep-username');
            const contentElements = screen.getAllByRole('peep-content');

            expect(usernameElements[1]).toHaveTextContent('User 2');
            expect(contentElements[1]).toHaveTextContent('another peep');
        });
    })

    describe('Testing comments and reply for peeps', () => {

        it("Clicking comments btn should open the comments section", async() => {
            const commentsBtn = screen.getAllByRole('open-comments-btn');
             await act(() =>{
                fireEvent.click(commentsBtn[0]);
            })


            const commentSection = screen.getByRole("comment-section");
            expect(commentSection).toBeInTheDocument();
        })

        it("Clicking reply btn should open reply and comment section", async () => {
            const replyBtn = screen.getAllByRole('reply-btn');
             await act(() =>{
                fireEvent.click(replyBtn[0]);
            })


            const commentForm = screen.getByRole("comment-form");
            expect(commentForm).toBeInTheDocument();

            const commentSection = screen.getByRole("comment-section");
            expect(commentSection).toBeInTheDocument();
        })

        it("Comments section should render 2 comments", async() => {
            const commentsBtn = screen.getAllByRole('open-comments-btn');
             await act(() => {
                fireEvent.click(commentsBtn[0]);
            })



            const comments = await waitFor(() => screen.getAllByRole("peep-comment"));
            expect(comments).toHaveLength(4);
        })


        describe("Testing comment elements load", () => {

            beforeEach(async() => {
                const commentsBtn = screen.getAllByRole('open-comments-btn');
                 await act(() =>{
                    fireEvent.click(commentsBtn[0]);
                })

                await waitFor(() => screen.getAllByRole("peep-comment"));
            })



            describe("Testing comment usernames load", () => {

                it("Should load username Tim", () => {
                    const commentUsername1 = screen.getByText("Tim");
                    expect(commentUsername1).toBeInTheDocument()
                })

                it("Should load username David", () => {
                    const commentUsername2 = screen.getByText("David");
                    expect(commentUsername2).toBeInTheDocument();
                })

                it("Should load username Lenka", () => {
                    const commentUsername3 = screen.getByText("Lenka");
                    expect(commentUsername3).toBeInTheDocument();
                })

                it("Should load username Lenka", () => {
                    const commentUsername4 = screen.getByText("Carry");
                    expect(commentUsername4).toBeInTheDocument();
                })
            })

            describe("Testing comment text load", () => {

                it("Should load the text of the fourth comment", () => {
                    const commentText1 = screen.getByText(/this is my forth/i);
                    expect(commentText1).toBeInTheDocument();
                })

                it("Should load the text of the third comment", () => {
                    const commentText2 = screen.getByText(/this is my third/i);
                    expect(commentText2).toBeInTheDocument();
                })

                it("Should load the text of the second comment", () => {
                    const commentText3 = screen.getByText(/This is my second comment/i);
                    expect(commentText3).toBeInTheDocument();
                })

                it("Should load the text of the first comment", () => {
                    const commentText4 = screen.getByText(/This is my first comment/i);
                    expect(commentText4).toBeInTheDocument();
                })

            })




        })

        describe("Testing reply form elements laod", () => {

            beforeEach(async() => {
                const replyBtn = screen.getAllByRole('reply-btn');
                 await act(async () =>{
                    fireEvent.click(replyBtn[0]);
                })


                await waitFor(() => screen.getAllByRole("peep-comment"));

            })

            it("Should load the post peep btn", () => {
                const postBtn = screen.getByRole("button", {name: "Reply"});
                expect(postBtn).toBeInTheDocument();
            })

            it("Should load the input box", () => {
                const postBtn = screen.getByRole("peep-reply-input-box");
                expect(postBtn).toBeInTheDocument();
            })
        })

    });


})