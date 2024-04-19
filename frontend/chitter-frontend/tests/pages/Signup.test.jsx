import Signup from "../../src/pages/Signup.jsx";
import {render, screen} from "@testing-library/react";
import {MemoryRouter} from "react-router-dom";
import {AuthProvider} from "../../src/services/context/AuthContext.jsx";


describe("Testing signup page", () => {

    beforeEach(async() => {
        await render(
            <MemoryRouter>
                <AuthProvider>
                    <Signup />
                </AuthProvider>
            </MemoryRouter>
        );
    })

    it("renders First Name input", () => {
        const fNameInput = screen.getByPlaceholderText("First Name");
        expect(fNameInput).toBeInTheDocument();
    });

    it("renders Last Name input", () => {
        const lNameInput = screen.getByPlaceholderText("Last Name");
        expect(lNameInput).toBeInTheDocument();
    });

    it("renders Username input", () => {
        const usernameInput = screen.getByPlaceholderText("Username");
        expect(usernameInput).toBeInTheDocument();
    });

    it("renders Email input", () => {
        const emailInput = screen.getByPlaceholderText("Email");
        expect(emailInput).toBeInTheDocument();
    });

    it("renders Password input", () => {
        const passwordInput = screen.getByPlaceholderText("Password");
        expect(passwordInput).toBeInTheDocument();
    });

    it("renders Sign Up button", () => {
        const signupBtn = screen.getByRole("button", { name: "Sign Up" });
        expect(signupBtn).toBeInTheDocument();
    });

    it("Should render the navbar", () => {

        const navbarElement = screen.getByRole("navigation");
        expect(navbarElement).toBeInTheDocument()
    });

    it("Should render the footer", () => {
        const footerElement = screen.getByRole("footer");
        expect(footerElement).toBeInTheDocument();
    })
})