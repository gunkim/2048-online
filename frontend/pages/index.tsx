import SettingOutlined from "@ant-design/icons/lib/icons/SettingOutlined"
import TeamOutlined from "@ant-design/icons/lib/icons/TeamOutlined"
import TrophyOutlined from "@ant-design/icons/lib/icons/TrophyOutlined"
import UserOutlined from "@ant-design/icons/lib/icons/UserOutlined"
import { Col, Row, Typography } from "antd"
import Link from "next/link"
import React from "react"
import styled from "styled-components"
const { Title, Text } = Typography

const MyTitle = styled(Title)`
  font-size: 6vh !important;
  color: white !important;
  color: ${props => `${props.color} !important;`};
`
const MyText = styled(Text)`
  color: ${props => (props.color ? props.color : "white")};
`
const SingleIcon = styled(UserOutlined)`
  font-size: 50px;
  color: white;
`
const MultiIcon = styled(TeamOutlined)`
  font-size: 50px;
  color: white;
`
const MyTrophyOutlined = styled(TrophyOutlined)`
  font-size: 50px;
  color: #413c69;
`
const MySettingOutlined = styled(SettingOutlined)`
  font-size: 50px;
  color: white;
`
const Tile = styled(Col)`
  min-height: ${props => props.height};
  background: ${props => props.background};
`
const Center = styled.div`
  padding: 50px;
`
export default function Home() {
  return (
    <div>
      <Row>
        <Tile xs={24} lg={15} height={"600px"} background={"#413C69"}>
          <Center>
            <MyTitle font={30}>
              <MultiIcon /> 멀티
            </MyTitle>
          </Center>
        </Tile>
        <Tile xs={24} lg={9} background={"#4A47A3"}>
          <Center>
            <MyTitle>
              <SingleIcon /> 싱글
            </MyTitle>
          </Center>
        </Tile>
        <Tile xs={24} lg={15} height={"369px"} background={"#ffffff"}>
          <Center>
            <MyTitle color={"#413C69"}>
              <MyTrophyOutlined /> 랭킹
            </MyTitle>
          </Center>
        </Tile>
        <Tile xs={24} lg={9} background={"#A7C5EB"}>
          <Center>
            <MyTitle>
              <MySettingOutlined /> 내 정보
            </MyTitle>
          </Center>
        </Tile>
      </Row>
    </div>
  )
}
