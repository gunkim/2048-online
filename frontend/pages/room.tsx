import { Alert, Box, Card, Grid, Snackbar, Tab, Tabs } from "@mui/material"
import { useRouter } from "next/router"
import React, { useEffect, useState } from "react"
import Layout from "../components/layout/Layout"
import RoomsFrame from "../components/RoomsFrame"
import { getUsername } from "../util/jwt-util"
import { exitRoom } from "../apis/room"
import { useDispatch, useSelector } from "react-redux"
import { RootState } from "../store"
import { connectSocketAsync } from "../store/socket/actions"

type GameInfo = {
  board: number[][]
  score: number
  gameOver: boolean
}
type Player = {
  nickname: string
  gameInfo: GameInfo | null
  ready: boolean
}
type GameRoom = {
  name: string
  players: Array<Player>
  gameMode: string
  maxNumberOfPeople: string
  start: boolean
  host: string
}
const Room = () => {
  const [gameInfo, setGameInfo] = useState<GameRoom>({
    name: "",
    players: [],
    gameMode: "",
    maxNumberOfPeople: "",
    start: false,
    host: ""
  })
  const router = useRouter()
  const dispatch = useDispatch()
  const { roomId } = router.query
  const [value, setValue] = useState(0)
  const [open, setOpen] = useState(false)
  const [username, setUsername] = useState("")
  const [message, setMessage] = useState(false)
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue)
  }
  const handleOpen = () => setOpen(true)
  const handleClose = () => setOpen(false)

  useEffect(() => {
    setUsername(getUsername())
  }, [username])

  const {
    loading,
    data: stompClient,
    error
  } = useSelector((state: RootState) => state.socket.socket)

  useEffect(() => {
    dispatch(connectSocketAsync.request(null, null))
  }, [dispatch])
  useEffect(() => {
    if (stompClient == null) {
      return
    }
    const headers = {
      Authorization: localStorage.getItem("token")
    }
    stompClient.connect(headers, () => {
      stompClient.send("/pub/multi/init", {}, roomId)
      stompClient.subscribe(`/sub/room/${roomId}`, response => {
        const payload: GameRoom = JSON.parse(response.body)
        console.log(payload)
        setGameInfo(payload)
      })
    })
  }, [stompClient])

  const handleExit = async () => {
    await exitRoom()
    router.push({
      pathname: "/rooms"
    })
  }
  const handleReady = () => {
    stompClient.send("/pub/multi/ready", {})
  }
  const handleStart = () => {
    if (gameInfo.players.filter(player => !player.ready).length > 0) {
      setMessage(true)
    }
    if (!gameInfo.start) {
      stompClient.send("/pub/multi/start", {})
      router.push("/multi?roomId=" + roomId)
    }
  }
  return (
    <Layout>
      {message && (
        <Snackbar
          anchorOrigin={{ vertical: "top", horizontal: "center" }}
          open={true}
          autoHideDuration={4000}
          onClose={() => setMessage(false)}
        >
          <Alert severity="warning" sx={{ width: "100%" }}>
            대기방의 인원 모두가 대기해야 게임을 시작할 수 있습니다.
          </Alert>
        </Snackbar>
      )}
      <Box sx={{ width: "100%" }}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <Tabs
            value={value}
            onChange={handleChange}
            aria-label="basic tabs example"
          >
            {gameInfo.host === username && !gameInfo.start && (
              <Tab
                label="시작"
                style={{
                  borderRadius: "10px 10px 0px 0px",
                  background: "#ffa5a5",
                  fontWeight: "bold",
                  color: "black"
                }}
                onClick={handleStart}
              />
            )}
            {!gameInfo.start &&
              (gameInfo.players.filter(
                player => player.nickname === username
              )[0] &&
              gameInfo.players.filter(player => player.nickname === username)[0]
                .ready ? (
                <Tab
                  label="준비 해제"
                  style={{
                    borderRadius: "10px 10px 0px 0px",
                    background: "#d3d3d3",
                    fontWeight: "bold",
                    color: "black"
                  }}
                  onClick={handleReady}
                />
              ) : (
                <Tab
                  label="준비"
                  style={{
                    borderRadius: "10px 10px 0px 0px",
                    background: "#b0ff59",
                    fontWeight: "bold",
                    color: "black"
                  }}
                  onClick={handleReady}
                />
              ))}

            <Tab
              label="나가기"
              onClick={handleExit}
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#dea1fd",
                fontWeight: "bold",
                color: "black"
              }}
            />
          </Tabs>
        </Box>
      </Box>
      <RoomsFrame>
        <Grid container spacing={2} marginLeft={0}>
          {gameInfo.players.map(player => (
            <Grid item xs={3}>
              {player.ready ? (
                <Card style={{ background: "#fffdbc", minHeight: "150px" }}>
                  {player.nickname}
                </Card>
              ) : (
                <Card style={{ minHeight: "150px" }}>{player.nickname}</Card>
              )}
            </Grid>
          ))}
        </Grid>
      </RoomsFrame>
    </Layout>
  )
}

export default Room
