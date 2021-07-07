import React from "react"
import { Card, Typography } from "antd"
import styled from "styled-components"
const { Title, Text } = Typography

const MyCard = styled(Card)`
  width: 300px;
  margin: 10px;
  float: left;
  cursor: pointer;
  text-align: center;
  font-weight: bold;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
`
const MyText = styled(Text)`
  word-break: keep-all;
`
type PlayingBoxProps = {
  icon: any
  title: string
  descript: string
}
const PlayingBox = ({ icon, title, descript }: PlayingBoxProps) => {
  return (
    <MyCard>
      {icon}
      <Title>{title}</Title>
      <MyText>{descript}</MyText>
    </MyCard>
  )
}

export default PlayingBox
