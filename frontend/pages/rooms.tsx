import React, { useEffect, useState } from "react"
import { Button, Form, Input, Modal, Radio, Row, Select } from "antd"
import Layout from "../components/Layout"
import RoomCard from "../components/RoomCard"
import stompClient from "../util/socket-util"
import RoomModal from "../components/RoomModal"

const Rooms = () => {
  const [rooms, setRooms] = useState()
  const [isModalVisible, setIsModalVisible] = useState(false)

  const handleOk = () => {
    setIsModalVisible(false)
  }

  const handleCancel = () => {
    setIsModalVisible(false)
  }
  const showModal = () => {
    setIsModalVisible(true)
  }
  useEffect(() => {
    const headers = {
      Authorization: localStorage.getItem("token")
    }
    stompClient.connect(headers, () => {
      stompClient.send("/game/rooms", {})
      stompClient.subscribe("/play/rooms", response => {
        const result = JSON.parse(response.body)
        setRooms(result.roomList)
      })
    })
  })
  return (
    <Layout>
      <Button type="primary" onClick={showModal}>
        방 만들기
      </Button>
      <hr />
      <Row>
        {rooms &&
          rooms.map((room, index) => (
            <RoomCard
              key={index}
              title={room.name}
              peopleCnt={room.players.length}
              gameMode={room.gameMode}
            />
          ))}
      </Row>
      <RoomModal
        isModalVisible={isModalVisible}
        handleOk={handleOk}
        handleCancel={handleCancel}
      />
    </Layout>
  )
}

export default Rooms
