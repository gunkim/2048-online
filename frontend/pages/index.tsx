import { Input } from "antd"
import "antd/dist/antd.css"
import React, { useRef, useState } from "react"
import { UserOutlined } from "@ant-design/icons"
import styled from "styled-components"
import { Typography } from "antd"
import { checkUser, User } from "../apis/user"
import { useDispatch } from "react-redux"
import { signInUserAsync } from "../store/actions/user"
const { Title } = Typography

const MyInput = styled(Input)`
  background: none;
  border: 1px solid black;
  border-radius: 15px;
  background: white;
`
const MyPassword = styled(Input.Password)`
  background: none;
  border: 1px solid black;
  border-radius: 15px;
  background: white;
`
const Frame = styled.div`
  width: 30%;
  margin: 0 auto;
  position: absolute;
  top: 30%;
  left: 35%;
`
const Samp = styled.span`
  background: #1f204e;
  color: white;
  padding: 1px 4px;
`

const Home = () => {
  const [user, setUser] = useState<User>({
    username: "",
    password: ""
  })
  const [check, setCheck] = useState()
  const inputRef = useRef(null)
  const dispatch = useDispatch()

  const onChange = (e) => {
    const name = e.target.name
    const value = e.target.value

    setUser({
      ...user,
      [name]: value
    })
  }
  const onKeyPress = async (e) => {
    if(e.key == 'Enter') {
      const name = e.target.name
      if(name == 'username') {
        const response = await checkUser(user.username);
        setCheck(response)
        inputRef.current.focus()
      } else {
        dispatch(signInUserAsync.request(user))
      }
    }
  }
  return (
    <Frame>
      <Title underline>2048</Title>
      <div className="input-wrapper">
        <label htmlFor="employee-id">
          <h1>nickname</h1>
        </label>
        <p id="employee-id-hint" className="input-hint">
          게임에서 사용할 닉네임을 입력해주세요
          <Samp>ex: 홍길동</Samp>
        </p>
        <MyInput
          size="large"
          placeholder="nickname"
          prefix={<UserOutlined />}
          name="username"
          value={user.username}
          onChange={onChange}
          onKeyPress={onKeyPress}
          id="employee-id"
          aria-describedby="employee-id-hint"
        />
        {check != undefined && (<>
        <label htmlFor="employee-id">
          <h1>password</h1>
        </label>
          <p id="employee-id-hint" className="input-hint">
            {check ? <div>이미 등록된 사용자입니다. 비밀번호를 입력해주세요.</div>:<div>등록되지 않은 사용자입니다. 비밀번호를 설정해주세요.</div>}
        </p>
        <MyPassword
          size="large"
          placeholder="password"
          prefix={<UserOutlined />}
          name="password"
          value={user.password}
          onChange={onChange}
          onKeyPress={onKeyPress}
          id="employee-id"
          aria-describedby="employee-id-hint"
          ref={inputRef}
        /></>)}
      </div>
    </Frame>
  )
}

export default Home
