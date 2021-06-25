import React from "react"
import { Provider } from "react-redux"
import { createStore, applyMiddleware, compose } from "redux"
import { composeWithDevTools } from "redux-devtools-extension"
import createSagaMiddleware from "redux-saga"
import rootReducer, { rootSaga } from "../store"

const sagaMiddleware = createSagaMiddleware()

const middlewares = [sagaMiddleware]
const enhancer =
  process.env.NODE_ENV === "production"
    ? compose(applyMiddleware(...middlewares))
    : composeWithDevTools(applyMiddleware(sagaMiddleware))

const store = createStore(rootReducer, enhancer)
sagaMiddleware.run(rootSaga)

type ReduxProvider = {
  children: React.ReactNode
}
const ReduxProvider = ({ children }) => (
  <Provider store={store}>{children}</Provider>
)
export default ReduxProvider
