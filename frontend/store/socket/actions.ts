import { createAsyncAction } from "typesafe-actions"
import { AxiosError } from "axios"
import Stomp from "stompjs"

export const CONNECT_SOCKET_REQUEST = "socket/CONNECT_SOCKET_REQUEST" as const
export const CONNECT_SOCKET_SUCCESS = "socket/CONNECT_SOCKET_SUCCESS" as const
export const CONNECT_SOCKET_FAILURE = "socket/CONNECT_SOCKET_FAILURE" as const
export const CONNECT_SOCKET_ALREADY = "socket/CONNECT_SOCKET_ALREADY" as const

export const connectSocketAsync = createAsyncAction(
  CONNECT_SOCKET_REQUEST,
  CONNECT_SOCKET_SUCCESS,
  CONNECT_SOCKET_FAILURE,
  CONNECT_SOCKET_ALREADY
)<undefined, Stomp.Client, AxiosError, Stomp.Client>()
