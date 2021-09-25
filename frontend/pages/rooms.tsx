import React, { useEffect, useState } from "react"
import { Button, Row } from "antd"
import Layout from "../components/Layout"
import RoomCard from "../components/RoomCard"
import RoomModal from "../components/RoomModal"
import { useDispatch, useSelector } from "react-redux"
import { RootState } from "../store"
import { getRoomsAsync } from "../store/rooms/actions"
import { useRouter } from "next/router"
import { createRoom, joinRoom, Room } from "../apis/room"

const Rooms = () => {
  const dispatch = useDispatch()
  const router = useRouter()
  const [isModalVisible, setIsModalVisible] = useState(false)

  useEffect(() => {
    dispatch(getRoomsAsync.request(null, null))
  }, [dispatch])

  const { loading, data, error } = useSelector(
    (state: RootState) => state.rooms.rooms
  )
  const handleOk = async (room: Room) => {
    const roomId = await createRoom(room)
    router.push({
      pathname: "/room",
      query: { roomId: roomId }
    })
  }
  const handleCancel = () => {
    setIsModalVisible(false)
  }
  const showModal = () => {
    setIsModalVisible(true)
  }
  const handleJoin = async (roomId: number) => {
    await joinRoom(roomId).then(() => {
      router.push({
        pathname: "/room",
        query: { roomId: roomId }
      })
    })
  }
  return (
    <Layout>
      <Button type="primary" onClick={showModal}>
        방 만들기
      </Button>
      <hr />
      <Row>
        {loading && <div>로딩중...</div>}
        {data &&
          data.map((room, index) => (
            <RoomCard
              key={index}
              id={room.id}
              title={room.title}
              people={room.participant}
              mode={room.mode}
              personnel={room.personnel}
              handleJoin={handleJoin}
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
