import axios from "axios";

const BASE_URL = '/api/v2/room' as const

export type Room = {
    number: string
    title: string
    username: string
    mode: string
    personnel: number
}

export const getRooms = async (): Promise<Room[]> => {
    const token: string = localStorage.getItem("token")
    const response = await axios.get(`${BASE_URL}/list`, {headers: {'Authorization': token}})
    return response.data
}