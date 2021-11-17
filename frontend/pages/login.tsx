import React, { useState } from "react"
import { useRouter } from "next/router"
import { User } from "../apis/user"
import { signInUserAsync } from "../store/user/actions"
import Layout from "../components/layout/Layout"
import { useDispatch, useSelector } from "react-redux"
import { RootState } from "../store"
import {
  Button,
  Card,
  Grid,
  Snackbar,
  CardContent,
  TextField,
  Typography,
  Alert
} from "@mui/material"

export default function Login() {
  const dispatch = useDispatch()
  const router = useRouter()
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
  const onSubmit = async e => {
    if (e.key === "Enter" || e.target.type === "button") {
      await dispatch(signInUserAsync.request(user))
    }
  }
  const { data, error } = useSelector((state: RootState) => state.user.signIn)

  if (data) {
    setTimeout(() => {
      router.push("/")
    }, 800)
  }
  return (
    <Layout>
      {data && (
        <Snackbar
          anchorOrigin={{ vertical: "top", horizontal: "center" }}
          open={true}
          message="로그인되었습니다!"
        >
          <Alert severity="success" sx={{ width: "100%" }}>
            환영합니다!
          </Alert>
        </Snackbar>
      )}
      {error && (
        <Snackbar
          anchorOrigin={{ vertical: "top", horizontal: "center" }}
          open={true}
          message="로그인되었습니다!"
        >
          <Alert severity="error" sx={{ width: "100%" }}>
            아이디 및 비밀번호를 다시 확인해주세요!
          </Alert>
        </Snackbar>
      )}
      <Card
        variant="outlined"
        sx={{ maxWidth: 800, margin: "0 auto", padding: "20px 10px" }}
      >
        <CardContent>
          <Grid container spacing={2}>
            <Grid item xs={12} md={6} component="form">
              <Typography variant="h3" align="center">
                로그인
              </Typography>
              <TextField
                name="username"
                variant="standard"
                placeholder="아이디를 입력해주세요"
                fullWidth
                margin="normal"
                value={user.username}
                onChange={onChange}
              />
              <br />
              <TextField
                name="password"
                variant="standard"
                type="password"
                placeholder="비밀번호를 입력해주세요"
                fullWidth
                margin="normal"
                value={user.password}
                onChange={onChange}
                onKeyPress={onSubmit}
              />
              <Button
                type="button"
                variant="contained"
                fullWidth
                onClick={onSubmit}
              >
                로그인
              </Button>
              <hr style={{ margin: "15px" }} />
              <Button variant="outlined" fullWidth>
                회원가입
              </Button>
            </Grid>
            <Grid item xs={12} md={6} component="form">
              <Card
                variant="outlined"
                sx={{
                  maxWidth: 800,
                  height: 274,
                  margin: "0 auto",
                  padding: "20px 10px"
                }}
              >
                <CardContent></CardContent>
              </Card>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
    </Layout>
  )
}
