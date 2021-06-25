import axios from "axios"

export const checkUser = async (username: string) => {
  const response = await axios.post(`/api/v2/member/check/${username}`)
  return response.data
}
