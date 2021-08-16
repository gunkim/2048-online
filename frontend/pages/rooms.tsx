import React, { useEffect, useState } from "react"
import { Button, Progress, Row } from "antd"
import Layout from "../components/Layout"
import RoomCard from "../components/RoomCard"
import RoomModal from "../components/RoomModal"
import { useDispatch, useSelector } from "react-redux"
import { RootState } from "../store"
import { createRoomAsync, getRoomsAsync } from "../store/rooms/actions"
import { useRouter } from "next/router"
import { Room } from "../apis/room"

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
  const roomId = useSelector((state: RootState) => state.rooms.room.data)
  if (roomId) {
    router.push({
      pathname: "/room",
      query: { roomId: roomId }
    })
  }

  const handleOk = (room: Room) => {
    dispatch(createRoomAsync.request(room))
    setIsModalVisible(false)
  }
  const handleCancel = () => {
    setIsModalVisible(false)
  }
  const showModal = () => {
    setIsModalVisible(true)
  }
  const handleJoin = (roomId: number) => {
    router.push({
      pathname: "/room",
      query: { roomId: roomId }
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
              people={1}
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
