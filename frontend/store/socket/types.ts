import { ActionType } from "typesafe-actions"
import * as actions from "../socket/actions"
import Stomp from "stompjs"

export type SocketAction = ActionType<typeof actions>

export type SocketState = {
  socket: {
    loading: boolean
    error: Error | null
    data: Stomp.Client | null
  }
}
