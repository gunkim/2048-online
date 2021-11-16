import jwtDecode, { JwtPayload } from "jwt-decode"

export const getUsername = (): string => {
    const token = localStorage.getItem("token")
    if(!token) {
        return ""
    }
    const payload: JwtPayload = jwtDecode(token);
    return payload.sub
}