import React from "react"
import { Card, Col, Row } from "antd"
import styled from "styled-components"
import { SettingOutlined, UserOutlined } from "@ant-design/icons"

const RoomFrame = styled(Col)`
  margin-top: 30px;
  padding: 10px;
`
type RoomCardType = {
  id: number
  title: string
  mode: string
  people: number
  personnel: number
  handleJoin: (roomId: number) => void
}
const RoomCard = ({
  id,
  title,
  mode,
  people,
  personnel,
  handleJoin
}: RoomCardType) => {
  return (
    <RoomFrame span={12}>
      <Card
        style={{ boxShadow: "0px 0px 5px 0px" }}
        title={title}
        extra={
          people != personnel && <a onClick={() => handleJoin(id)}>입장하기</a>
        }
      >
        <p>
          <SettingOutlined /> {mode}
        </p>
        <p>
          <UserOutlined /> {people}/{personnel}
        </p>
      </Card>
    </RoomFrame>
  )
}

export default RoomCard
