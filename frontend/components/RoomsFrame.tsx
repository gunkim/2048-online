import {
  Box,
  Card,
  CardContent,
  Grid,
  List,
  ListItem,
  ListItemText,
  Modal,
  Tab,
  Tabs,
  Typography
} from "@mui/material"
import React, { ReactNode, useState } from "react"
import RoomCreateForm from "./RoomCreateForm"

type RoomsFrameProps = {
  children: ReactNode
}
const RoomsFrame = ({ children }: RoomsFrameProps) => {
  const [value, setValue] = useState(0)
  const [open, setOpen] = useState(false)
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue)
  }
  const handleOpen = () => setOpen(true)
  const handleClose = () => setOpen(false)
  return (
    <>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <RoomCreateForm />
      </Modal>
      <Box sx={{ width: "100%" }}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <Tabs
            value={value}
            onChange={handleChange}
            aria-label="basic tabs example"
          >
            <Tab
              label="방 만들기"
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#aeadff",
                fontWeight: "bold",
                color: "black"
              }}
              onClick={handleOpen}
            />
            <Tab
              label="랭킹"
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#e38a8a",
                fontWeight: "bold",
                color: "black"
              }}
            />
            <Tab
              label="싱글"
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#a5ffa4",
                fontWeight: "bold",
                color: "black"
              }}
            />
          </Tabs>
        </Box>
      </Box>
      <Card style={{ marginBottom: "10px" }}>
        <CardContent>
          <Grid container spacing={2} style={{ marginTop: 0 }}>
            <Grid item xs={9.5}>
              <Grid
                container
                spacing={2}
                style={{ overflow: "auto", height: 570 }}
              >
                {children}
              </Grid>
            </Grid>
            <Grid item xs={2.5}>
              <Typography variant="h6" component="div">
                접속자
              </Typography>
              <List style={{ overflow: "auto", height: 535 }}>
                <ListItem style={{ padding: "0px 16px" }}>
                  <ListItemText style={{ margin: 0 }} primary="인원1" />
                </ListItem>
              </List>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
    </>
  )
}
export default RoomsFrame
