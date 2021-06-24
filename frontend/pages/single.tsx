import styled from "styled-components"
import "antd/dist/antd.css"
import { Row, Col } from "antd"
import { Level } from "../style/Level"
import { useEffect, useState } from "react"
import SockJS from "sockjs-client"
import Stomp from "stompjs"
import hotkeys from "hotkeys-js"
import GameBoard from "../components/GameBoard"

const Frame = styled(Row)`
  width: 20%;
  text-align: center;
  margin: 0 auto;

  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
`

const Title = styled(Col)`
  font-size: 4rem;
  font-weight: bold;
`

const ScoreBox = styled(Col)`
  font-weight: bold;
  font-size: 1rem;
`

type Game = {
  board: number[][]
  score: number
}

let sockJS = new SockJS("http://localhost:8080/webSocket")
let stompClient: Stomp.Client = Stomp.over(sockJS)
stompClient.debug = () => {
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
  const test = () => {
    stompClient.send("/game/start", {})
  }
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
  }, [])
  const headers = {
    Authorization:
      "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0TWFuIiwicm9sZXMiOlsiVVNFUiJdLCJpc3MiOiJjbG9uZS1tYXJrZXQiLCJpYXQiOjE2MjQ0OTQxNzAsImV4cCI6MTczMjQ5NDE3MH0.bZaPxge4bXDmfcBR5YHflU2Rt9p9u-h9zMZbSP4NjAN3RfPOU01MInIS80I7dS8g6zSqxAhC7JA4xoNGFdAxZw"
  }

  stompClient.connect(headers, () => {
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
  return (
    <Frame>
      <Row style={{ width: "100%" }}>
        <Title span={12}>2048</Title>
        <ScoreBox span={12}>
          <h3>SCORE</h3>
          <p>{game.score}</p>
        </ScoreBox>
      </Row>
      <GameBoard board={game.board} />
      <button onClick={test}>테스트</button>
    </Frame>
  )
}

export default Single
