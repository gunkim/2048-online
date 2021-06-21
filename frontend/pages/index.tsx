import styled from "styled-components"
import "antd/dist/antd.css"
import { Row, Col } from "antd"
import { Level } from "../style/Level"
import { useEffect, useState } from "react"
import SockJS from "sockjs-client"
import Stomp from "stompjs"

const Frame = styled(Row)`
  width: 40%;
  text-align: center;
  margin: 0 auto;
`
const Title = styled.h1`
  width: 50%;
  text-align: center;
  margin: 0 auto;
  margin-top: 30px;
  margin-bottom: 30px;
  font-size: 4em;
  padding: 10px;
  color: #00b7c2;
  font-weight: bold;
  border: 5px solid #00b7c2;
  border-radius: 10px;
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
const StartButton = styled.span`
  margin: 5px;
  float: right;
  border-radius: 5px;
  padding: 5px;
  background: #ff3939;
  color: white;
  cursor: pointer;
  font-size: 1.5rem;
  margin-bottom: 20px;
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
  useEffect(() => {
    stompClient.connect({}, () => {
      stompClient.subscribe("/play/start", response => {
        const payload = JSON.parse(response.body)
        setGame(payload)
      })
    })
  }, [setGame])
  const test = () => {
    stompClient.send("/game/start", {}, "hello, gunkim!")
  }
  return (
    <>
      <h1>{game.score}</h1>
      <Title>Playing!</Title>
      <Frame>
        <StartButton onClick={test}>게임 시작</StartButton>
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
    </>
  )
}

export default Home
