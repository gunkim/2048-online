import { combineReducers } from "redux"
import { all } from "redux-saga/effects"
import user from "./user/reducer"
import rooms from './rooms/reducer'
import socket from './socket/reducer'
import { userSaga } from "./user/sagas"
import { roomSaga } from "./rooms/sagas";
import { socketSaga } from "./socket/sagas"

const rootReducer = combineReducers({
  user,
  rooms,
  socket
})

export default rootReducer

export type RootState = ReturnType<typeof rootReducer>

export function* rootSaga() {
  yield all([userSaga(), roomSaga(), socketSaga()])
}
