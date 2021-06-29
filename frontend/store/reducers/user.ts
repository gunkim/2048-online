import { createReducer } from "typesafe-actions"
import { checkUserAsync } from "../actions/user"
import { UserAction, UserState } from "../types/user"

const initialState: UserState = {
  checkUser: {
    loading: false,
    error: null,
    data: null
  }
}

const user = createReducer<UserState, UserAction>(initialState)
  .handleAction(checkUserAsync.request, state => {
    return {
      ...state,
      loading: true,
      error: null,
      data: null
    }
  })
  .handleAction(checkUserAsync.success, (state, action) => {
    return {
      ...state,
      loading: false,
      error: null,
      data: action.payload
    }
  })
  .handleAction(checkUserAsync.failure, (state, action) => {
    return {
      ...state,
      loading: false,
      error: action.payload
    }
  })

export default user
