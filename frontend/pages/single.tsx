import "antd/dist/antd.css"
import { useEffect, useState } from "react"
import hotkeys from "hotkeys-js"
import GameBoard from "../components/GameBoard"
import stompClient from "../util/socket-util"
import Layout from "../components/Layout"
import styled from "styled-components"
import { getUsername } from "../util/jwt-util"

const ScoreBox = styled.div`
  background: #97cdff;
  padding: 50px;
  border-radius: 15px;
  position: fixed;
  left: 15%;
  top: 30%;
  text-align: center;
  h2 {
    font-weight: bold;
  }
`

type Game = {
  board: number[][]
  score: number
  gameOver: boolean
}

const Single = () => {
  const [game, setGame] = useState<Game>({
    board: [
      [0, 0, 0, 0],
      [0, 0, 0, 0],
      [0, 0, 0, 0],
      [0, 0, 0, 0]
    ],
    score: 0,
    gameOver: false
  })
  const leftMove = e => {
    e.preventDefault()
    if (!game.gameOver) {
      stompClient.send("/pub/single/left", {})
    }
  }
  const rightMove = e => {
    e.preventDefault()
    if (!game.gameOver) {
      stompClient.send("/pub/single/right", {})
    }
  }
  const topMove = e => {
    e.preventDefault()

    if (!game.gameOver) {
      stompClient.send("/pub/single/top", {})
    }
  }
  const bottomMove = e => {
    e.preventDefault()

    if (!game.gameOver) {
      stompClient.send("/pub/single/bottom", {})
    }
  }
  let memberId: string
  useEffect(() => {
    console.log("DOM LOAD")
    memberId = getUsername()
    hotkeys("left", leftMove)
    hotkeys("right", rightMove)
    hotkeys("up", topMove)
    hotkeys("down", bottomMove)

    const headers = {
      Authorization: localStorage.getItem("token")
    }
    stompClient.connect(headers, () => {
      stompClient.send("/pub/single/init", {})
      stompClient.subscribe(`/sub/single/${memberId}`, response =>
        setGame(JSON.parse(response.body))
      )
    })
  }, [])

  return (
    <Layout width={518}>
      <ScoreBox>
        <h2>SCORE</h2>
        <h3>{game.score}</h3>
      </ScoreBox>
      <GameBoard
        mainWidth={470}
        board={game.board}
        width={100}
        height={100}
        over={game.gameOver}
      />
    </Layout>
  )
}

export default Single
