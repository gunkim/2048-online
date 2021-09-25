import axios from "axios";

const BASE_URL = '/api/v2/room' as const

export type Room = {
    id?: number
    title: string
    username?: string
    mode: string
    personnel: number
    participant: number
}

export const getRooms = async (): Promise<Room[]> => {
    const token: string = localStorage.getItem("token")

    const response = await axios.get(`${BASE_URL}/list`, {headers: {'Authorization': token}})
    return response.data
}
export const createRoom = async (room: Room) => {
    const token: string = localStorage.getItem("token")
    const response = await axios({
        method: 'post',
        url: BASE_URL,
        headers: {'Authorization': token},
        data: room
    })
    return response.data
}
export const joinRoom = async (roomId: number) => {
    const token: string = localStorage.getItem("token")
    await axios({
        method: 'put',
        url: `${BASE_URL}/join/${roomId}`,
        headers: {'Authorization': token},
    })
}
export const exitRoom = async() => {
    const token: string = localStorage.getItem("token")
    await axios({
        method: 'put',
        url: `${BASE_URL}/exit`,
        headers: {'Authorization': token},
    })
}