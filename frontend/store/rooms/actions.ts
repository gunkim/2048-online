import {createAsyncAction} from "typesafe-actions";
import {AxiosError} from "axios";
import {Room} from "../../apis/room";

export const GET_ROOMS_REQUEST = 'rooms/GET_ROOMS_REQUEST' as const
export const GET_ROOMS_SUCCESS = 'rooms/GET_ROOMS_SUCCESS' as const
export const GET_ROOMS_FAILURE = 'rooms/GET_ROOMS_FAILURE' as const

export const getRoomsAsync = createAsyncAction(
    GET_ROOMS_REQUEST,
    GET_ROOMS_SUCCESS,
    GET_ROOMS_FAILURE
)<undefined, Room[], AxiosError>()