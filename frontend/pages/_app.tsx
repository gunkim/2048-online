import "../style/global.css"
import ReduxProvider from "../components/ReduxProvider"

const MyApp = ({ Component, pageProps }) => {
  return (
    <ReduxProvider>
      <Component {...pageProps} />
    </ReduxProvider>
  )
}

export default MyApp
