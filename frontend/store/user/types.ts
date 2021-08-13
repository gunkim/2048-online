import { ActionType } from "typesafe-actions"
import * as actions from "../user/actions"

export type UserAction = ActionType<typeof actions>

export type UserState = {
  signIn: {
    loading: boolean
    error: Error | null
    data: string | null
  }
}
