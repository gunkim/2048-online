import { checkUser, signIn } from "../../apis/user"
import { signInUserAsync, SIGN_IN_USER_REQUEST } from "../actions/user"
import { call, put, takeEvery } from "redux-saga/effects"

function* signInUserSaga(action: ReturnType<typeof signInUserAsync.request>) {
  try {
    const jwtToken: string = yield call(signIn, action.payload)
    localStorage.setItem('token', jwtToken)
    yield put(signInUserAsync.success(jwtToken))
  } catch (e) {
    yield put(signInUserAsync.failure(e))
  }
}

export function* userSaga() {
  yield takeEvery(SIGN_IN_USER_REQUEST, signInUserSaga)
}
