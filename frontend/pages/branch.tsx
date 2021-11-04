import React from "react"
import { User, UserNew } from "grommet-icons"
import { Grommet, Anchor, Box, Button, Text, Paragraph, Heading } from "grommet"
import styled from "styled-components"
import { useRouter } from "next/router"

const MyGrommet = styled(Grommet)`
  height: 100%;
`

export default function Branch() {
  const router = useRouter()
  return (
    <MyGrommet>
      <Box
        style={{ height: "100%" }}
        direction="row-responsive"
        justify="center"
        align="center"
        pad="xlarge"
        gap="medium"
      >
        <Box
          pad="large"
          align="center"
          background={{ color: "light-5", opacity: "strong" }}
          round
          gap="small"
        >
          <UserNew size="xlarge" />
          <Heading margin="none" level={2}>
            같이
          </Heading>
          <Paragraph margin="none">
            2048 게임을 여러 사람과 함께 즐겨보세요!
          </Paragraph>
          <Button
            label="시작하기"
            onClick={() => {
              router.push("/rooms")
            }}
          />
        </Box>
        <Box pad="large" align="center" background="dark-2" round gap="small">
          <User size="xlarge" color="light-2" />
          <Heading margin="none" color="white" level={2}>
            혼자
          </Heading>
          <Paragraph margin="none" color="white">
            혼자 플레이하며 다른 사람들과 순위 경쟁을 해보세요!
          </Paragraph>
          <Button
            label="시작하기"
            onClick={() => {
              router.push("/single")
            }}
          />
        </Box>
      </Box>
    </MyGrommet>
  )
}
