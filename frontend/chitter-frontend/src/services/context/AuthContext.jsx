import {useContext, useState, createContext} from "react";

const AuthContext = createContext();

export function AuthProvider({children}){
    const [fName, setFName] = useState("");
    const [lName, setlName] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [loggedIn, setLoggedIn] = useState(false);

    function handleLogin({fName, lName, email, username}){
        try {
            setFName(fName);
            setlName(lName);
            setUsername(username);
            setEmail(email);
            setLoggedIn(true);
        } catch (error) {
            console.error(error);
        }
    }

    function handleLogout(){
        try {
            setFName("");
            setlName("");
            setEmail("");
            setUsername("");
            setLoggedIn(false);
        } catch (error) {
            console.error(error);
        }
    }

    return(
        <AuthContext.Provider
            value={{fName, lName, email, username, loggedIn, handleLogin, handleLogout}}>
            {children}
        </AuthContext.Provider>
    )
}

export function useAuth(){
    return useContext(AuthContext);
}