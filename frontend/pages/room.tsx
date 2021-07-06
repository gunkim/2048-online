import Layout from "../components/Layout"
import { Col, Row } from "antd"
import GameBoard from "../components/GameBoard"
import { useEffect, useState } from "react"
import stompClient from "../util/socket-util"
import hotkeys from "hotkeys-js"

type Game = {
  board: number[][]
  score: number
}
const Room = () => {
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
        <Col span={12}>
          <GameBoard board={game.board} />
        </Col>{" "}
        <Col span={12}>
          <GameBoard board={game.board} />
        </Col>{" "}
        <Col span={12}>
          <GameBoard board={game.board} />
        </Col>{" "}
        <Col span={12}>
          <GameBoard board={game.board} />
        </Col>
      </Row>
    </Layout>
  )
}

export default Room
