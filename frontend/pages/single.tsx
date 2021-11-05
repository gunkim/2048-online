import React, { useEffect, useState } from "react"
import hotkeys from "hotkeys-js"
import GameBoard from "../components/GameBoard"
import stompClient from "../util/socket-util"
import styled from "styled-components"
import { getUsername } from "../util/jwt-util"
import { Grommet, Box, Grid } from "grommet"

const ScoreBox = styled.span`
  text-align: center;
  color: black;
  font-weight: bold;
  h2 {
    font-weight: bold;
    color: white;
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
    <Grommet full>
      <Grid
        rows={["xsmall", "medium"]}
        areas={[["header"], ["main"]]}
        gap="small"
      >
        <Box
          gridArea="header"
          direction="column"
          justify="center"
          align="center"
        >
          <ScoreBox>
            <h2>SCORE</h2>
            <div>{game.score}</div>
          </ScoreBox>
        </Box>
        <Box gridArea="main">
          <GameBoard board={game.board} over={game.gameOver} />
        </Box>
      </Grid>
    </Grommet>
  )
}

export default Single
