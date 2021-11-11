import React, { useState } from "react"
import {
  Box,
  Button,
  Grommet,
  Form,
  FormField,
  TextInput,
  Notification,
  grommet
} from "grommet"
import { useRouter } from "next/router"
import { User } from "../apis/user"
import { useDispatch } from "react-redux"
import { signInUserAsync } from "../store/user/actions"

export default function Login() {
  const dispatch = useDispatch()
  const router = useRouter()
  const [user, setUser] = useState<User>({
    username: "",
    password: ""
  })
  const [isLogin, setIsLogin] = useState(false)
  const onChange = (value: User) => {
    setUser(value)
  }
  const onSubmit = async () => {
    await dispatch(signInUserAsync.request(user))
    setIsLogin(true)
    router.back()
  }
  return (
    <Grommet full theme={grommet}>
      <Box fill align="center" justify="center">
        <Box width="medium">
          <Form
            value={user}
            onChange={nextValue => onChange(nextValue)}
            onSubmit={onSubmit}
          >
            <FormField label="Username" name="username" required>
              <TextInput
                name="username"
                type="username"
                placeholder="유저이름을 입력해주세요"
              />
            </FormField>
            <FormField label="Password" name="password" required>
              <TextInput
                name="password"
                type="password"
                placeholder="비밀번호를 입력해주세요"
              />
            </FormField>
            <Box direction="row" justify="between" margin={{ top: "medium" }}>
              <Button
                type="reset"
                label="홈으로"
                onClick={() => router.push("/")}
              />
              <Button type="submit" label="로그인" primary />
            </Box>
          </Form>
        </Box>
      </Box>
      {isLogin && (
        <Notification
          toast
          status="normal"
          title="로그인되었습니다."
          message="환영합니다!"
        />
      )}
    </Grommet>
  )
}
