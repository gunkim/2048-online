import { SocketAction, SocketState } from "../socket/types"
import { createReducer } from "typesafe-actions"
import { connectSocketAsync } from "../socket/actions"

const initialState: SocketState = {
  socket: {
    loading: false,
    error: null,
    data: null
  }
}

const room = createReducer<SocketState, SocketAction>(initialState)
  .handleAction(connectSocketAsync.request, state => {
    return {
      ...state,
      socket: {
        loading: true,
        error: null,
        data: state.socket.data
      }
    }
  })
  .handleAction(connectSocketAsync.success, (state, action) => {
    return {
      ...state,
      socket: {
        loading: false,
        error: null,
        data: action.payload
      }
    }
  })
  .handleAction(connectSocketAsync.failure, (state, action) => {
    return {
      ...state,
      socket: {
        loading: false,
        error: action.payload,
        data: null
      }
    }
  })
  .handleAction(connectSocketAsync.cancel, (state, action) => {
    return {
      ...state,
      socket: {
        loading: false,
        error: null,
        data: action.payload
      }
    }
  })

export default room
