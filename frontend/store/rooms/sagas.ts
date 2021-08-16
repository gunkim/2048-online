import {GET_ROOMS_REQUEST, getRoomsAsync, createRoomAsync, CREATE_ROOM_REQUEST} from "../rooms/actions";
import {call, put, takeEvery} from "redux-saga/effects";
import * as Api from '../../apis/room'

function* getRooms() {
    try {
        const rooms: Api.Room[] = yield call(Api.getRooms)
        yield put(getRoomsAsync.success(rooms))
    } catch(e) {
        yield put(getRoomsAsync.failure(e))
    }
}
function* createRoom(action: ReturnType<typeof createRoomAsync.request>) {
    try {
        const roomId: number = yield call(Api.createRoom, action.payload)
        yield put(createRoomAsync.success(roomId))
    } catch(e) {
        yield put(createRoomAsync.failure(e))
    }
}

export function* roomSaga() {
    yield takeEvery(GET_ROOMS_REQUEST, getRooms)
    yield takeEvery(CREATE_ROOM_REQUEST, createRoom)
}