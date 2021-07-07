import { checkUser, signIn } from "../../apis/user"
import { signInUserAsync, SIGN_IN_USER_REQUEST } from "../actions/user"
import { call, put, takeEvery } from "redux-saga/effects"
import Router from "next/router"

function* signInUserSaga(action: ReturnType<typeof signInUserAsync.request>) {
  try {
    const jwtToken: string = yield call(signIn, action.payload)
    yield put(signInUserAsync.success(jwtToken))
    localStorage.setItem("token", jwtToken)
    yield call(Router.push, "/branch")
  } catch (e) {
    yield put(signInUserAsync.failure(e))
  }
}

export function* userSaga() {
  yield takeEvery(SIGN_IN_USER_REQUEST, signInUserSaga)
}
