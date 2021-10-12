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
  font-size: 3rem !important;
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
const MyRow = styled(Row)`
  @media screen and (max-width: 2000px) {
    .top {
      height: 600px;
    }
    .bottom {
      height: 369px;
    }
  }
  @media screen and (max-width: 1366px) {
    .top {
      height: 489px;
    }
    .bottom {
      height: 200px;
    }
  }
`
const Tile = styled(Col)`
  background: ${props => props.background};
  padding: 50px;
`
export default function Home() {
  return (
    <div>
      <MyRow>
        <Tile xs={24} lg={15} className="top" background={"#413C69"}>
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
        <Tile xs={24} lg={15} className="bottom" background={"#ffffff"}>
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
      </MyRow>
    </div>
  )
}
