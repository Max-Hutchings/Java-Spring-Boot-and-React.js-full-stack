import React, { useEffect } from "react";
import { useAuth } from "../../../src/services/context/AuthContext.jsx";
import Home from "../../../src/pages/Home.jsx";

const mockLoginData = {
    fName: "Test",
    lName: "User",
    email: "test@example.com",
    username: "testuser",
};

function RenderHomeWithLoggedIn() {
    const { handleLogin } = useAuth();

    useEffect(() => {
        handleLogin(mockLoginData);
    }, [handleLogin]);

    return <Home />;
}

export default RenderHomeWithLoggedIn;
