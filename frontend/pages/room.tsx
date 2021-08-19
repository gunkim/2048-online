import Layout from "../components/Layout"
import GameBoard from "../components/GameBoard"
import React, { useEffect, useState } from "react"
import styled from "styled-components"
import { Typography } from "antd"
import stompClient from "../util/socket-util"
import { useRouter } from "next/router"
import hotkeys from "hotkeys-js"

const { Title } = Typography

const MainFrame = styled.div`
  background-color: yellow;
  float: left;
  border-radius: 10px;
  border: 2px solid black;
`
const Frame = styled.div`
  float: left;
  background-color: #ceefff;
  border-radius: 10px;
  margin: 4px;
`
const Head = styled.div`
  background-color: #ff8585;
  color: white;
`
const Info = styled.div`
  text-align: center;
  padding-top: 10px;

  h3 {
    color: white;
    text-shadow: -2px 0 black, 0 2px black, 2px 0 black, 0 -2px black;
  }
`
const ScoreBox = styled.div`
  text-align: center;
  color: white;
  h2 {
    font-weight: bold;
    color: white;
  }
  h3 {
    color: white;
  }
`
const Room = () => {
  const router = useRouter()
  const [gameInfo, setGameInfo] = useState({
    gameMode: "SPEED_ATTACK",
    maxNumberOfPeople: "FOUR",
    name: "새로운 방입니당~",
    players: [
      {
        gameInfo: { board: new Array(), score: 0, gameOver: false },
        nickname: ""
      }
    ],
    start: false,
    timer: 0
  })
  const leftMove = e => {
    e.preventDefault()
    stompClient.send("/game/multi/left", {})
  }
  const rightMove = e => {
    e.preventDefault()
    stompClient.send("/game/multi/right", {})
  }
  const topMove = e => {
    e.preventDefault()
    stompClient.send("/game/multi/top", {})
  }
  const bottomMove = e => {
    e.preventDefault()
    stompClient.send("/game/multi/bottom", {})
  }
  useEffect(() => {
    console.log("DOM LOAD")
    hotkeys("left", leftMove)
    hotkeys("right", rightMove)
    hotkeys("up", topMove)
    hotkeys("down", bottomMove)

    const { roomId } = router.query
    const headers = {
      Authorization: localStorage.getItem("token")
    }
    stompClient.connect(headers, () => {
      stompClient.send("/game/room", {}, roomId)
      stompClient.subscribe("/play/room", response => {
        const payload = JSON.parse(response.body)
        setGameInfo(payload)
      })
      stompClient.subscribe("/play/multi/left", response => {
        const payload = JSON.parse(response.body)
        console.log(payload)
        setGameInfo(payload)
      })

      stompClient.subscribe("/play/multi/top", response => {
        const payload = JSON.parse(response.body)
        console.log(payload)
        setGameInfo(payload)
      })

      stompClient.subscribe("/play/multi/right", response => {
        const payload = JSON.parse(response.body)
        console.log(payload)
        setGameInfo(payload)
      })

      stompClient.subscribe("/play/multi/bottom", response => {
        const payload = JSON.parse(response.body)
        console.log(payload)
        setGameInfo(payload)
      })
    })
  }, [router])
  return (
    <Layout width={610}>
      <MainFrame>
        {gameInfo.players.map((player, index) => (
          <Frame key={index}>
            <Head>
              <Info>
                <Title level={3}>{player.nickname}</Title>
              </Info>
              <ScoreBox>
                <h3>SCORE {player.gameInfo.score}</h3>
              </ScoreBox>
            </Head>
            <GameBoard
              board={player.gameInfo.board}
              mainWidth={270}
              width={50}
              height={50}
              over={player.gameInfo.gameOver}
            />
          </Frame>
        ))}
      </MainFrame>
    </Layout>
  )
}

export default Room
