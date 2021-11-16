import { Box, Card, CardContent, Tab, Tabs } from "@mui/material"
import React from "react"
import Layout from "../components/new/Layout"
import RoomItem from "../components/RoomItem"
import RoomsFrame from "../components/RoomsFrame"

const Rooms = () => {
  return (
    <Layout>
      <RoomsFrame>
        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12].map(() => {
          return <RoomItem />
        })}
      </RoomsFrame>
    </Layout>
  )
}

export default Rooms
