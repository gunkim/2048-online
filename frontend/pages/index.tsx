import { Input, message } from "antd"
import React, { useRef, useState } from "react"
import { UserOutlined } from "@ant-design/icons"
import styled from "styled-components"
import { checkUser, User } from "../apis/user"
import { useDispatch, useSelector } from "react-redux"
import { signInUserAsync } from "../store/actions/user"
import Layout from "../components/Layout"
import { RootState } from "../store"
import Router from "next/router"
import Title from "antd/lib/typography/Title"

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
const Samp = styled.span`
  background: #1f204e;
  color: white;
  padding: 1px 4px;
`
const key = "updatable"

const Home = () => {
  const [user, setUser] = useState<User>({
    username: "",
    password: ""
  })
  const [check, setCheck] = useState()
  const inputRef = useRef(null)
  const dispatch = useDispatch()
  const { loading, error, data } = useSelector(
    (state: RootState) => state.user.signIn
  )
  if (data) {
    message.success({ content: "로그인 완료!", key, duration: 2 })
    Router.push("/branch")
  }
  if (loading) {
    message.loading({ content: "로그인 중...", key })
  }
  if (error) {
    message.error({
      content: "로그인에 실패했습니다!",
      key
    })
  }
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
      const name = e.target.name
      if (name == "username") {
        const response = await checkUser(user.username)
        setCheck(response)
        inputRef.current.focus()
      } else {
        dispatch(signInUserAsync.request(user))
      }
    }
  }
  return (
    <Layout>
      <div className="input-wrapper">
        <label htmlFor="employee-id">
          <Title level={2}>Nickname</Title>
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
        {check != undefined && (
          <>
            <label htmlFor="employee-id">
              <Title level={2}>Password</Title>
            </label>
            <p id="employee-id-hint" className="input-hint">
              {check ? (
                <div>이미 등록된 사용자입니다. 비밀번호를 입력해주세요.</div>
              ) : (
                <div>등록되지 않은 사용자입니다. 비밀번호를 설정해주세요.</div>
              )}
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
            />
          </>
        )}
      </div>
    </Layout>
  )
}

export default Home
