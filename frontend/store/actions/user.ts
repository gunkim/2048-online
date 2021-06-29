import { AxiosError } from "axios"
import { createAsyncAction } from "typesafe-actions"

export const CHECK_USER_REQUEST = "user/CHECK_USER_REQUEST" as const
export const CHECK_USER_SUCCESS = "user/CHECK_USER_SUCCESS" as const
export const CHECK_USER_FAILURE = "user/CHECK_USER_FAILURE" as const

export const checkUserAsync = createAsyncAction(
  CHECK_USER_REQUEST,
  CHECK_USER_SUCCESS,
  CHECK_USER_FAILURE
)<string, boolean, AxiosError>()
