import styled from "styled-components"
import "antd/dist/antd.css"
import { Row, Col } from "antd"
import { Level } from "../style/Level"
import { useEffect, useState } from "react"
import SockJS from "sockjs-client"
import Stomp from "stompjs"
import hotkeys from "hotkeys-js"

const Frame = styled(Row)`
  width: 40%;
  text-align: center;
  margin: 0 auto;
`
const Board = styled(Row)`
  width: 100%;
  background-color: #d9c6a5;
  padding: 7px;
  border-radius: 5px;
`
const Tile = styled.div`
  background-color: ${props =>
    props.lv != undefined ? Level[`LV${props.lv}`] : Level["LV0"]};
  padding: 5px;
  color: black;
  height: 140px;
  border-radius: 5px;
  margin: 7px;
  font-weight: bold;
  font-size: 3rem;
  line-height: 130px;
`
const StartButton = styled.div`
  margin: 5px;
  border-radius: 5px;
  padding: 5px;
  background: #ff3939;
  color: white;
  cursor: pointer;
  font-size: 1.5rem;
  margin-bottom: 20px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
`
const ScoreBox = styled.div`
  background: #ff9595;
  color: white;
  border-radius: 5px;
  padding: 5px;
  margin-bottom: 100px;
  margin-top: 10px;
  font-weight: bold;
  font-size: 1rem;
  h3 {
    color: white;
  }
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
`

type Game = {
  board: number[][]
  score: number
}

let sockJS = new SockJS("http://localhost:8080/webSocket")
let stompClient: Stomp.Client = Stomp.over(sockJS)
stompClient.debug = () => {}

const Home = () => {
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
  stompClient.connect({}, () => {
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
      <div>
        <ScoreBox>
          <h3>SCORE</h3>
          <p>{game.score}</p>
        </ScoreBox>
        <StartButton onClick={test}>게임 시작</StartButton>
      </div>
      <Board>
        {game.board.map(row =>
          row.map((col, index) => (
            <Col key={index} span={6}>
              <Tile lv={col}>{col != 0 ? Math.pow(2, col) : ""}</Tile>
            </Col>
          ))
        )}
      </Board>
    </Frame>
  )
}

export default Home
