import { createGlobalStyle } from "styled-components";
import {checkUser, User} from "../apis/user";
import {useState} from "react";
import {signInUserAsync} from "../store/user/actions";
import {useDispatch} from "react-redux";

const GlobalStyle = createGlobalStyle`
  body {
    background-color: white !important;
  }
`;

export default function Login() {
    const dispatch = useDispatch()
    const [user, setUser] = useState<User>({
        username: "",
        password: ""
    })
    const onChange = e => {
        const name = e.target.name
        const value = e.target.value

        setUser({
            ...user,
            [name]: value
        })
    }
    const onKeyPress = async e => {
        if (e.key == "Enter") {
            dispatch(signInUserAsync.request(user))
        }
    }
    return (<>
        <input name="username" value={user.username} onChange={onChange}/>
        <input name="password" value={user.password} onChange={onChange} onKeyPress={onKeyPress}/>
    </>)
}