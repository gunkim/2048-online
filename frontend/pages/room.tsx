import { Box, Card, Grid, Tab, Tabs } from "@mui/material"
import React, { useState } from "react"
import Layout from "../components/layout/Layout"
import RoomsFrame from "../components/RoomsFrame"

const Room = () => {
  const [value, setValue] = useState(0)
  const [open, setOpen] = useState(false)
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue)
  }
  const handleOpen = () => setOpen(true)
  const handleClose = () => setOpen(false)
  return (
    <Layout>
      <Box sx={{ width: "100%" }}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <Tabs
            value={value}
            onChange={handleChange}
            aria-label="basic tabs example"
          >
            {true ? (
              <Tab
                label="준비"
                style={{
                  borderRadius: "10px 10px 0px 0px",
                  background: "#b0ff59",
                  fontWeight: "bold",
                  color: "black"
                }}
                onClick={handleOpen}
              />
            ) : (
              <Tab
                label="준비 해제"
                style={{
                  borderRadius: "10px 10px 0px 0px",
                  background: "#d3d3d3",
                  fontWeight: "bold",
                  color: "black"
                }}
              />
            )}
            {false && (
              <Tab
                label="시작"
                style={{
                  borderRadius: "10px 10px 0px 0px",
                  background: "#ffa5a5",
                  fontWeight: "bold",
                  color: "black"
                }}
              />
            )}
            <Tab
              label="나가기"
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#dea1fd",
                fontWeight: "bold",
                color: "black"
              }}
            />
          </Tabs>
        </Box>
      </Box>
      <RoomsFrame>
        <Grid container spacing={2} marginLeft={0}>
          <Grid item xs={3}>
            <Card style={{ minHeight: "150px" }}>사람1</Card>
          </Grid>
          <Grid item xs={3}>
            <Card style={{ minHeight: "150px" }}>사람1</Card>
          </Grid>
          <Grid item xs={3}>
            <Card style={{ minHeight: "150px" }}>사람1</Card>
          </Grid>
          <Grid item xs={3}>
            <Card style={{ minHeight: "150px" }}>사람1</Card>
          </Grid>
        </Grid>
      </RoomsFrame>
    </Layout>
  )
}

export default Room
