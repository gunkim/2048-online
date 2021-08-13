import { ActionType } from "typesafe-actions"
import * as actions from "../actions/room"
import {Room} from "../../apis/room";

export type RoomAction = ActionType<typeof actions>

export type RoomState = {
    rooms: {
        loading: boolean
        error: Error | null
        data: Room[] | null
    }
}
