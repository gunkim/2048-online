import { CONNECT_SOCKET_REQUEST, connectSocketAsync } from "../socket/actions"
import { call, put, select, takeEvery } from "redux-saga/effects"
import SockJS from "sockjs-client"
import Stomp from "stompjs"

function* connectSocket() {
  try {
    const sockJS = new SockJS(`${process.env.NEXT_PUBLIC_SERVER_IP}/webSocket`)
    const stompClient: Stomp.Client = yield call(Stomp.over, sockJS)
    stompClient.debug = msg => {
      console.log(msg)
    }

    const { data } = yield select(store => store.socket.socket)
    if (data) {
      yield put(connectSocketAsync.cancel(stompClient))
    } else {
      yield put(connectSocketAsync.success(stompClient))
    }
  } catch (e) {
    console.log(e)
    yield put(connectSocketAsync.failure(e))
  }
}
export function* socketSaga() {
  yield takeEvery(CONNECT_SOCKET_REQUEST, connectSocket)
}
