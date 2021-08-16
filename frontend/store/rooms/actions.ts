import {createAsyncAction} from "typesafe-actions";
import {AxiosError} from "axios";
import {Room} from "../../apis/room";

export const GET_ROOMS_REQUEST = 'rooms/GET_ROOMS_REQUEST' as const
export const GET_ROOMS_SUCCESS = 'rooms/GET_ROOMS_SUCCESS' as const
export const GET_ROOMS_FAILURE = 'rooms/GET_ROOMS_FAILURE' as const

export const CREATE_ROOM_REQUEST = 'rooms/CREATE_ROOM_REQUEST' as const
export const CREATE_ROOM_SUCCESS = 'rooms/CREATE_ROOM_SUCCESS' as const
export const CREQTE_ROOM_FAILURE = 'rooms/CREATE_ROOM_FAILURE' as const

export const getRoomsAsync = createAsyncAction(
    GET_ROOMS_REQUEST,
    GET_ROOMS_SUCCESS,
    GET_ROOMS_FAILURE
)<undefined, Room[], AxiosError>()

export const createRoomAsync = createAsyncAction(
    CREATE_ROOM_REQUEST,
    CREATE_ROOM_SUCCESS,
    CREQTE_ROOM_FAILURE
)<Room, number, AxiosError>()