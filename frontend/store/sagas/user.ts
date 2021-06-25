import { checkUser } from "../../apis/user"
import { checkUserAsync, CHECK_USER_REQUEST } from "../actions/user"
import { call, put, takeEvery } from "redux-saga/effects"

function* checkUserSaga(action: ReturnType<typeof checkUserAsync.request>) {
  try {
    const userProfile: boolean = yield call(checkUser, action.payload)
    yield put(checkUserAsync.success(userProfile))
  } catch (e) {
    yield put(checkUserAsync.failure(e))
  }
}

export function* userSaga() {
  yield takeEvery(CHECK_USER_REQUEST, checkUserSaga)
}
