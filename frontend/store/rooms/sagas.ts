import {GET_ROOMS_REQUEST, getRoomsAsync} from "../rooms/actions";
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

export function* roomSaga() {
    yield takeEvery(GET_ROOMS_REQUEST, getRooms)
}