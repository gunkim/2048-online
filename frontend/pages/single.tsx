import "antd/dist/antd.css"
import { Row, Col } from "antd"
import { useEffect, useState } from "react"
import hotkeys from "hotkeys-js"
import GameBoard from "../components/GameBoard"
import stompClient from "../util/socket-util"
import Layout from "../components/Layout"
import styled from "styled-components"

const ScoreBox = styled(Col)`
  background: #97cdff;
  padding: 50px;
  border-radius: 15px;
`
const GameName = styled(Col)`
  padding: 50px;
  border-radius: 15px;
  font-size: 3rem;
`

type Game = {
  board: number[][]
  score: number
}

const Single = () => {
  const [game, setGame] = useState<Game>({
    board: [
      [0, 0, 0, 0],
      [0, 0, 0, 0],
      [0, 0, 0, 0],
      [0, 0, 0, 0]
    ],
    score: 0
  })
  const leftMove = () => {
    stompClient.send("/game/left", {})
  }
  const rightMove = () => {
    stompClient.send("/game/right", {})
  }
  const topMove = () => {
    stompClient.send("/game/top", {})
  }
  const bottomMove = () => {
    stompClient.send("/game/bottom", {})
  }
  useEffect(() => {
    console.log("DOM LOAD")
    hotkeys("left", leftMove)
    hotkeys("right", rightMove)
    hotkeys("up", topMove)
    hotkeys("down", bottomMove)

    const headers = {
      Authorization: localStorage.getItem("token")
    }
    stompClient.connect(headers, () => {
      stompClient.send("/game/start", {})
      stompClient.subscribe("/play/start", response => {
        const payload = JSON.parse(response.body)
        setGame(payload)
      })
      stompClient.subscribe("/play/left", response => {
        const payload = JSON.parse(response.body)
        setGame(payload)
      })
      stompClient.subscribe("/play/right", response => {
        const payload = JSON.parse(response.body)
        setGame(payload)
      })
      stompClient.subscribe("/play/top", response => {
        const payload = JSON.parse(response.body)
        setGame(payload)
      })
      stompClient.subscribe("/play/bottom", response => {
        const payload = JSON.parse(response.body)
        setGame(payload)
      })
    })
  }, [])

  return (
    <Layout>
      <Row>
        <GameName span={12}>Single</GameName>
        <ScoreBox span={12}>
          <h3>SCORE</h3>
          <p>{game.score}</p>
        </ScoreBox>
        <Col span={24}>
          <GameBoard board={game.board} />
        </Col>
      </Row>
    </Layout>
  )
}

export default Single
