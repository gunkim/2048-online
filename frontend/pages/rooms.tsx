import React, { useEffect, useState } from "react"
import { Avatar, Button, Col, List, Row, Skeleton, Typography } from "antd"
import Layout from "../components/Layout"
import RoomCard from "../components/RoomCard"
import RoomModal from "../components/RoomModal"
import { useDispatch, useSelector } from "react-redux"
import { RootState } from "../store"
import { getRoomsAsync } from "../store/rooms/actions"
import { useRouter } from "next/router"
import { createRoom, joinRoom, Room } from "../apis/room"
import Header from "../components/Header"
import Link from "next/link"
import styled from "styled-components"

const MyList = styled(List)`
  background: #c5d4ff;
  margin-top: 10px;

  a {
    font-size: 1.5rem;
  }
`

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
    <div>
      <Header />
      <Row justify="center">
        <Col xs={23} lg={10}>
          <Button type="primary" onClick={showModal}>
            방 만들기
          </Button>
          {data && (
            <MyList
              size="large"
              loading={loading}
              itemLayout="horizontal"
              header={<div>게임 목록</div>}
              bordered
              dataSource={data}
              renderItem={room => (
                <List.Item>
                  <List.Item.Meta
                    title={
                      <>
                        <Typography.Text mark>{room.mode}</Typography.Text>
                        {"  "}
                        <a onClick={() => handleJoin(room.id)}>{room.title}</a>
                      </>
                    }
                    description={`인원수 ${room.personnel}/${room.participant}`}
                  />
                </List.Item>
              )}
            />
          )}
        </Col>
      </Row>
      <RoomModal
        isModalVisible={isModalVisible}
        handleOk={handleOk}
        handleCancel={handleCancel}
      />
    </div>
  )
}

export default Rooms
