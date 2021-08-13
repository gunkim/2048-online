import { combineReducers } from "redux"
import { userSaga } from "./user/sagas"
import { all } from "redux-saga/effects"
import user from "./user/reducer"
import rooms from './rooms/reducer'
import {roomSaga} from "./rooms/sagas";

const rootReducer = combineReducers({
  user,
  rooms
})

export default rootReducer

export type RootState = ReturnType<typeof rootReducer>

export function* rootSaga() {
  yield all([userSaga(), roomSaga()])
}
