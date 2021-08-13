import {RoomAction, RoomState} from "../rooms/types"
import {createReducer} from "typesafe-actions";
import {getRoomsAsync} from "../rooms/actions";

const initialState: RoomState = {
    rooms: {
        loading: false,
        error: null,
        data: null
    }
}

const room = createReducer<RoomState, RoomAction>(initialState)
    .handleAction(getRoomsAsync.request, state => {
        return {
            ...state,
            rooms: {
                loading: true,
                error: null,
                data: null
            }
        }
    })
    .handleAction(getRoomsAsync.success, (state, action) => {
        return {
            ...state,
            rooms: {
                loading: false,
                error: null,
                data: action.payload
            }
        }
    })
    .handleAction(getRoomsAsync.failure, (state, action) => {
        return {
            ...state,
            rooms: {
                loading: false,
                error: action.payload,
                data: null
            }
        }
    })

export default room
