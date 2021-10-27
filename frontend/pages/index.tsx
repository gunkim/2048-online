import SettingOutlined from "@ant-design/icons/lib/icons/SettingOutlined"
import TeamOutlined from "@ant-design/icons/lib/icons/TeamOutlined"
import TrophyOutlined from "@ant-design/icons/lib/icons/TrophyOutlined"
import UserOutlined from "@ant-design/icons/lib/icons/UserOutlined"
import Link from "next/link"
import React from "react"
import styled from "styled-components"

const MyTitle = styled.div`
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
const Frame = styled.table`
  margin: 0 auto;

  @media screen and (max-width: 2000px) {
    width: 1000px;
  }
  @media screen and (max-width: 1366px) {
    width: 600px;
  }
  tr {
    height: 130px;
  }
  td {
    text-align: center;
  }
`
export default function Home() {
  return (
    <Frame>
      <tr>
        <td style={{ background: "#413C69" }}>
          <MyTitle>
            <Link href="/rooms">
              <a>
                <MultiIcon /> 멀티
              </a>
            </Link>
          </MyTitle>
        </td>
        <td rowSpan={2} style={{ background: "#403c5e" }}>
          <MyTitle>
            <Link href="/single">
              <a>
                <SingleIcon /> 싱글
              </a>
            </Link>
          </MyTitle>
        </td>
      </tr>
      <tr>
        <td style={{ background: "#ffffff" }}>
          <MyTitle color="#413C69">
            <Link href="/single">
              <a>
                <MyTrophyOutlined /> 랭킹
              </a>
            </Link>
          </MyTitle>
        </td>
      </tr>
      <tr>
        <td colSpan={2} style={{ background: "#A7C5EB" }}>
          <MyTitle>
            <Link href="/single">
              <a>
                <MySettingOutlined /> 내 정보
              </a>
            </Link>
          </MyTitle>
        </td>
      </tr>
    </Frame>
  )
}
