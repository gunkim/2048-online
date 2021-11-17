import {
  Box,
  Grid,
  List,
  ListItem,
  ListItemText,
  Modal,
  Tab,
  Tabs,
  Typography
} from "@mui/material"
import React, { useEffect, useState } from "react"
import Layout from "../components/layout/Layout"
import RoomItem from "../components/RoomItem"
import RoomsFrame from "../components/RoomsFrame"
import { getRoomsAsync } from "../store/rooms/actions"
import { RootState } from "../store"
import { useDispatch, useSelector } from "react-redux"
import { Room } from "../apis/room"
import RoomSkeletonItem from "./../components/RoomSkeletonItem"
import RoomCreateForm from "../components/RoomCreateForm"

const Rooms = () => {
  const dispatch = useDispatch()
  const [value, setValue] = useState(0)
  const [open, setOpen] = useState(false)
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue)
  }
  const handleOpen = () => setOpen(true)
  const handleClose = () => setOpen(false)
  useEffect(() => {
    dispatch(getRoomsAsync.request(null, null))
  }, [dispatch])

  const { loading, data, error } = useSelector(
    (state: RootState) => state.rooms.rooms
  )
  return (
    <Layout>
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
      <RoomsFrame>
        <Grid item xs={9.5}>
          <Grid container spacing={2} style={{ overflow: "auto", height: 570 }}>
            {loading && (
              <>
                {[1, 2, 3, 4, 5, 6, 7, 8].map(() => (
                  <RoomSkeletonItem />
                ))}
              </>
            )}
            {data &&
              data.map((room: Room) => {
                return (
                  <RoomItem
                    key={room.id}
                    id={room.id}
                    title={room.title}
                    mode={room.mode}
                    participant={room.participant}
                    personnel={room.personnel}
                  />
                )
              })}
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
      </RoomsFrame>
    </Layout>
  )
}

export default Rooms
