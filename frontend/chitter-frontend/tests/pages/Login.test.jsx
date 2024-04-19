import {MemoryRouter} from "react-router-dom";
import Login from "../../src/pages/Login.jsx";
import {render, screen} from "@testing-library/react";
import {AuthProvider} from "../../src/services/context/AuthContext.jsx";


describe("Testing the login page", () => {

    beforeEach(async() => {
        await render(
            <MemoryRouter>
                <AuthProvider>
                    <Login />
                </AuthProvider>
            </MemoryRouter>
        )
    })

    it("Should render email input", () => {
        const emailInput = screen.getByPlaceholderText("Email");
        expect(emailInput).toBeInTheDocument();

    })

    it("Should render the password input", () => {
        const passwordInput = screen.getByPlaceholderText("Password");
        expect(passwordInput).toBeInTheDocument();
    })

    it("Should render the login submit form btn", () => {
        const loginBtn = screen.getByRole("button", {name: "Login"});
        expect(loginBtn).toBeInTheDocument();
    })


    it("Should render the navbar", () => {
        const navbarElement = screen.getByRole("navigation");
        expect(navbarElement).toBeInTheDocument()

    });

    it("Should render the footer", () => {
        const footerElement = screen.getByRole("footer");
        expect(footerElement).toBeInTheDocument();

    })
})