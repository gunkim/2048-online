import React from "react"
import { Card, Col, Row } from "antd"
import styled from "styled-components"
import { SettingOutlined, UserOutlined } from "@ant-design/icons"

const RoomFrame = styled(Col)`
  margin-top: 30px;
  padding: 10px;
`
type RoomCardType = {
  title: string
  gameMode: string
  peopleCnt: number
}
const RoomCard = ({ title, gameMode, peopleCnt }: RoomCardType) => {
  return (
    <RoomFrame span={12}>
      <Card
        style={{ boxShadow: "0px 0px 5px 0px" }}
        title={title}
        extra={<a href="#">입장하기</a>}
      >
        <p>
          <SettingOutlined /> {gameMode}
        </p>
        <p>
          <UserOutlined /> {peopleCnt}/4
        </p>
      </Card>
    </RoomFrame>
  )
}

export default RoomCard
