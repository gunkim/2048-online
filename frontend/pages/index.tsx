import React, { useEffect, useState } from "react"
import {
  Button,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  Typography
} from "@mui/material"
import { List } from "grommet"
import Layout from "../components/layout/Layout"
import Grid from "@mui/material/Grid"
import { useRouter } from "next/router"
import { getUsername } from "../util/jwt-util"

const data = []

for (let i = 0; i < 5; i += 1) {
  data.push({
    entry: `2021.11.15.`,
    location: `안녕하세요`
  })
}
const Home = () => {
  const router = useRouter()
  const [name, setName] = useState("")
  const [value, setValue] = useState("one")

  useEffect(() => {
    setName(getUsername())
  }, [setName])
  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue)
  }
  return (
    <Layout>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Card
            sx={{ maxWidth: 345, textAlign: "center", margin: "0 auto" }}
            onClick={() => {
              if (name) {
                router.push("/rooms")
              } else {
                router.push("/login")
              }
            }}
          >
            <CardActionArea>
              <CardContent>
                {name ? (
                  <Typography gutterBottom variant="h5" component="div">
                    게임 시작
                  </Typography>
                ) : (
                  <Typography gutterBottom variant="h5" component="div">
                    로그인
                  </Typography>
                )}
              </CardContent>
            </CardActionArea>
            {!name && (
              <CardActions style={{ background: "#f7f7f7" }}>
                <Button
                  size="small"
                  color="primary"
                  style={{ margin: "0 auto" }}
                >
                  소셜 로그인
                </Button>
              </CardActions>
            )}
          </Card>
        </Grid>
        <Grid item xs={12} md={6}>
          <Typography
            variant="h4"
            component="h1"
            style={{ marginBottom: "33px" }}
          >
            공지사항
          </Typography>
          <List data={data} primaryKey="entry" secondaryKey="location" />
        </Grid>
        <Grid item xs={12} md={6}>
          <Typography
            variant="h4"
            component="h1"
            style={{ marginBottom: "33px" }}
          >
            소통
          </Typography>
          <List data={data} primaryKey="entry" secondaryKey="location" />
        </Grid>
        <Grid item xs={12}>
          <Typography
            variant="h4"
            component="h1"
            style={{ marginBottom: "33px" }}
          >
            랭킹
          </Typography>
          <List data={data} primaryKey="entry" secondaryKey="location" />
        </Grid>
      </Grid>
    </Layout>
  )
}

export default Home
