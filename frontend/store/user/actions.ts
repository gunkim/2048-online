import { AxiosError } from "axios"
import { createAsyncAction } from "typesafe-actions"
import { User } from "../../apis/user"

export const SIGN_IN_USER_REQUEST = 'user/SIGN_IN_USER_REQUEST' as const
export const SIGN_IN_USER_SUCCESS = 'user/SIGN_IN_USER_SUCCESS' as const
export const SIGN_IN_USER_FAILURE = 'user/SIGN_IN_USER_FAILURE' as const

export const signInUserAsync = createAsyncAction(
  SIGN_IN_USER_REQUEST,
  SIGN_IN_USER_SUCCESS,
  SIGN_IN_USER_FAILURE
)<User, string, AxiosError>()
