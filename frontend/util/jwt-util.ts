import jwtDecode, { JwtPayload } from "jwt-decode"

export const getUsername = () => {
    const payload: JwtPayload = jwtDecode(localStorage.getItem("token"));
    return payload.sub
}