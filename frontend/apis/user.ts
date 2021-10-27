import axios from "axios"

const BASE_URL = "/api/v2/member" as const

export type User = {
  username: string
  password: string
}

export const checkUser = async (username: string) => {
  const response = await axios.get(`${BASE_URL}/check/${username}`)
  return response.data
}
export const signIn = async (user: User) => {
  const response = await axios.post(`${BASE_URL}/signIn`, user)
  const { data } = response.data
  return data.accessToken
}
