import { combineReducers } from "redux"
import { userSaga } from "./sagas/user"
import { all } from "redux-saga/effects"
import user from "./reducers/user"

const rootReducer = combineReducers({
  user
})

export default rootReducer

export type RootState = ReturnType<typeof rootReducer>

export function* rootSaga() {
  yield all([userSaga()])
}
