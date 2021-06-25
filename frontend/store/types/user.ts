import { ActionType } from "typesafe-actions"
import * as actions from "../actions"

export type UserAction = ActionType<typeof actions>

export type UserState = {
  checkUser: {
    loading: boolean
    error: Error | null
    data: string | null
  }
}
