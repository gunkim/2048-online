import React, { useEffect, useState } from "react"
import { Button, Progress, Row } from "antd"
import Layout from "../components/Layout"
import RoomCard from "../components/RoomCard"
import RoomModal from "../components/RoomModal"
import { useDispatch, useSelector } from "react-redux"
import { RootState } from "../store"
import { getRoomsAsync } from "../store/rooms/actions"

const Rooms = () => {
  const dispatch = useDispatch()

  useEffect(() => {
    dispatch(getRoomsAsync.request(null, null))
  }, [dispatch])

  const { loading, data, error } = useSelector(
    (state: RootState) => state.rooms.rooms
  )

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
              title={room.title}
              people={1}
              mode={room.mode}
              personnel={room.personnel}
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
