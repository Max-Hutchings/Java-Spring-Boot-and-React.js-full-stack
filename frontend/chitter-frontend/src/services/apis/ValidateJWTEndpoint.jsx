import axios from "axios";

export default async function callValidateJWT() {
    try {
        const response = await axios.get("http://localhost:4000/authentication/validate-jwt", {
            withCredentials: true
        });
        return response;
    } catch (error) {
        return { status: error.response?.status || 500, valid: false };
    }
}
