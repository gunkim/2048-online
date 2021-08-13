import { createReducer } from "typesafe-actions"
import { signInUserAsync } from "../user/actions"
import { UserAction, UserState } from "../user/types"

const initialState: UserState = {
  signIn: {
    loading: false,
    error: null,
    data: null
  }
}

const user = createReducer<UserState, UserAction>(initialState)
  .handleAction(signInUserAsync.request, state => {
    return {
      ...state,
      signIn: {
        loading: true,
        error: null,
        data: null
      }
    }
  })
  .handleAction(signInUserAsync.success, (state, action) => {
    return {
      ...state,
      signIn: {
        loading: false,
        error: null,
        data: action.payload
      }
    }
  })
  .handleAction(signInUserAsync.failure, (state, action) => {
    return {
      ...state,
      signIn: {
        loading: false,
        error: action.payload,
        data: null
      }
    }
  })

export default user