import {
  Button,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  CardMedia,
  Container,
  Typography
} from "@mui/material"
import { List, ResponsiveContext, Text } from "grommet"
import React, { useContext } from "react"
import Layout from "../components/new/Layout"
import { styled } from "@mui/material/styles"
import Paper from "@mui/material/Paper"
import Grid from "@mui/material/Grid"

const data = []

for (let i = 0; i < 5; i += 1) {
  data.push({
    entry: `2021.11.15.`,
    location: `안녕하세요`
  })
}
const Home = () => {
  const [value, setValue] = React.useState("one")
  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue)
  }
  return (
    <Layout>
      <Container maxWidth="md">
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Card sx={{ maxWidth: 345, textAlign: "center", margin: "0 auto" }}>
              <CardActionArea>
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    로그인
                  </Typography>
                </CardContent>
              </CardActionArea>
              <CardActions style={{ background: "#f7f7f7" }}>
                <Button
                  size="small"
                  color="primary"
                  style={{ margin: "0 auto" }}
                >
                  소셜 로그인
                </Button>
              </CardActions>
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
      </Container>
    </Layout>
  )
}

export default Home
