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
  a {
    color: white !important;
    color: ${props => `${props.color} !important;`};
  }
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
  padding: 50px;
`
const Center = styled.div``
export default function Home() {
  return (
    <div>
      <Row>
        <Tile xs={24} lg={15} height={"600px"} background={"#413C69"}>
          <MyTitle font={30}>
            <Link href="/rooms">
              <a>
                <MultiIcon /> 멀티
              </a>
            </Link>
          </MyTitle>
        </Tile>
        <Tile xs={24} lg={9} background={"#4A47A3"}>
          <MyTitle>
            <Link href="/single">
              <a>
                <SingleIcon /> 싱글
              </a>
            </Link>
          </MyTitle>
        </Tile>
        <Tile xs={24} lg={15} height={"369px"} background={"#ffffff"}>
          <MyTitle color={"#413C69"}>
            <Link href="/single">
              <a>
                <MyTrophyOutlined /> 랭킹
              </a>
            </Link>
          </MyTitle>
        </Tile>
        <Tile xs={24} lg={9} background={"#A7C5EB"}>
          <MyTitle>
            <Link href="/single">
              <a>
                <MySettingOutlined /> 내 정보
              </a>
            </Link>
          </MyTitle>
        </Tile>
      </Row>
    </div>
  )
};