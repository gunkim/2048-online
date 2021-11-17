import { Box, Card, CardContent, Tab, Tabs } from "@mui/material"
import React, { useEffect } from "react"
import Layout from "../components/new/Layout"
import RoomItem from "../components/RoomItem"
import RoomsFrame from "../components/RoomsFrame"
import { getRoomsAsync } from "../store/rooms/actions"
import { RootState } from "../store"
import { useDispatch, useSelector } from "react-redux"
import { Room } from "../apis/room"

const Rooms = () => {
  const dispatch = useDispatch()
  useEffect(() => {
    dispatch(getRoomsAsync.request(null, null))
  }, [dispatch])

  const { loading, data, error } = useSelector(
    (state: RootState) => state.rooms.rooms
  )
  return (
    <Layout>
      <RoomsFrame>
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
      </RoomsFrame>
    </Layout>
  )
}

export default Rooms
