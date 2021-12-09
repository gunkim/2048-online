import {Alert, Avatar, Card, CircularProgress, Grid, Snackbar, Tab, Tabs} from "@mui/material"
import {useRouter} from "next/router"
import React, {useEffect, useState} from "react"
import Layout from "../components/layout/Layout"
import RoomsFrame from "../components/RoomsFrame"
import {getUsername} from "../util/jwt-util"
import {exitRoom} from "../apis/room"
import {useDispatch, useSelector} from "react-redux"
import {RootState} from "../store"
import {connectSocketAsync} from "../store/socket/actions"
import {createGlobalStyle} from "styled-components";
import styled from 'styled-components'
import {Heading, Box, Button, TextInput} from "grommet";
import {User} from "grommet-icons";

const GlobalStyle = createGlobalStyle`
  body {
    background-color: #d2d2ff !important;
  }
`;
const Chating = styled.input`
    background: white;
    width: 100%;
    height: 45px; 
    border: none;
    border-radius: 10px 0px 10px 0px; 
    padding-left: 15px;
    font-weight: bold;
`
const ChatingList = styled.ul`
    width: 100%; 
    min-height: 540px;
    max-height: 540px;
    margin: 0px;
    list-style: none;
    color: white;
    overflow: scroll;
    
    span {
        color: #ff3c3c;
    }
`
const InfoBox = styled(Box)`
    background: #b8b7f7;
    width: 100%;
    height: 200px;
    border-radius: 0px 0px 0px 10px;
`
const Players = styled.div`
    background: white;
    width: 100%;
    height: 410px;
    border-radius: 10px 0px 0px 0px;
`

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

type RoomInfoProps = {
    username: string
    gameInfo: GameRoom
    handleReady: () => void
    handleStart: () => void
}
const RoomInfo = ({username, gameInfo, handleReady, handleStart}: RoomInfoProps) => {
    const isHost = gameInfo.host === username
    if (isHost) {
        const isParticipant = gameInfo.players.length > 1
        const isAllReady = gameInfo.players.filter(player => !player.ready).length == 0
        if (isParticipant && isAllReady) {
            return (
                <>
                    <Heading margin="none" textAlign="center" level={2}>게임을 시작할까요?</Heading>
                    <Box pad="xsmall">
                        <Button primary label="시작" onClick={handleStart}/>
                    </Box>
                </>
            )
        } else if (!isParticipant) {
            return (
                <Heading margin="none" textAlign="center" level={2}>사람이 없어 게임을 시작할 수 없어요!</Heading>
            )
        } else {
            return (
                <Heading margin="none" textAlign="center" level={2}>모든 참가자가 준비를 완료해야 게임을 시작할 수 있어요!</Heading>
            )
        }
    } else {
        const isReady = gameInfo.players.filter(player => player.nickname === username)[0] && gameInfo.players.filter(player => player.nickname === username)[0].ready

        if (isReady) {
            return (
                <Heading margin="none" textAlign="center" level={2}>게임이 시작되기 전까지 기다려주세요!</Heading>
            )
        } else {
            return (
                <>
                    <Heading margin="none" textAlign="center" level={2}>게임을 시작하기 위해 준비해주세요!</Heading>
                    <Box pad="xsmall">
                        <Button primary label="준비" onClick={handleReady}/>
                    </Box>
                </>
            )
        }
    }
    return <></>
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
    const {roomId} = router.query
    const [username, setUsername] = useState("")
    const [message, setMessage] = useState(false)

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
            <GlobalStyle/>
            {message && (
                <Snackbar
                    anchorOrigin={{vertical: "top", horizontal: "center"}}
                    open={true}
                    autoHideDuration={4000}
                    onClose={() => setMessage(false)}
                >
                    <Alert severity="warning" sx={{width: "100%"}}>
                        대기방의 인원 모두가 대기해야 게임을 시작할 수 있습니다.
                    </Alert>
                </Snackbar>
            )}
            <RoomsFrame>
                <Grid item xs={9} style={{padding: 0}}>
                    <Players>
                        <Grid container spacing={1} style={{minHeight: '100%', margin: 0}} textAlign="center">
                            {gameInfo.players.map(player => (
                                <Grid item xs={6} style={{padding: 0, minHeight: '102.5px'}}>
                                    <Box direction="column"
                                         align="center"
                                         justify="center"
                                         style={{height: '100%'}}
                                    >
                                        <Box>
                                            <Avatar
                                                alt={player.nickname}
                                                src="#"
                                                sx={{width: 100, height: 100}}
                                            />
                                        </Box>
                                    </Box>
                                </Grid>
                            ))}
                            {gameInfo.maxNumberOfPeople && new Array(parseInt(gameInfo.maxNumberOfPeople) - gameInfo.players.length).fill(0).map(() =>
                                <Grid item xs={6} style={{padding: 0, minHeight: '102.5px'}}>
                                    <Box direction="column"
                                         pad="medium"
                                         align="center"
                                         justify="center"
                                         style={{height: '100%'}}
                                    >
                                        <Box>
                                            <CircularProgress size={120} style={{color: '#bc75ff'}}/>
                                        </Box>
                                    </Box>
                                </Grid>)}
                        </Grid>
                    </Players>
                    <InfoBox
                        direction="column"
                        pad="medium"
                        align="center"
                        justify="center"
                    >
                        <RoomInfo username={username} gameInfo={gameInfo} handleReady={handleReady}
                                  handleStart={handleStart}/>
                    </InfoBox>
                </Grid>
                <Grid item xs={3}
                      style={{background: '#4b3aa9', borderRadius: '0px 10px 10px 0px', paddingRight: '8px'}}>
                    <ChatingList>
                        <li><span>개방장 : </span>안녕하세요</li>
                    </ChatingList>
                    <Chating placeholder="채팅을 입력해주세요"/>
                </Grid>
            </RoomsFrame>
        </Layout>
    )
}

export default Room
