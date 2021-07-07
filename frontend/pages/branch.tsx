import React from "react"
import { TeamOutlined, UserOutlined } from "@ant-design/icons"
import Layout from "../components/Layout"
import styled from "styled-components"
import Link from "next/link"
import PlayingBox from "../components/PlayingBox"

const SingleIcon = styled(UserOutlined)`
  font-size: 50px;
  color: #08c;
`
const MultiIcon = styled(TeamOutlined)`
  font-size: 50px;
  color: #08c;
`

const Branch = () => {
  return (
    <Layout>
      <div style={{ width: 640, margin: "0 auto" }}>
        <Link href="/single">
          <PlayingBox
            icon={<SingleIcon />}
            title="싱글"
            descript="혼자 플레이하여 점수를 내어 다른 유저들과 순위 경쟁을 할 수
              있습니다."
          />
        </Link>
        <Link href="/rooms">
          <PlayingBox
            icon={<MultiIcon />}
            title="멀티"
            descript="여러 유저들과 함께 실시간으로 경쟁을 해볼 수 있습니다."
          />
        </Link>
      </div>
    </Layout>
  )
}

export default Branch
