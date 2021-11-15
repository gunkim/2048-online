import { Grommet, Main } from "grommet"
import { grommet } from "grommet/themes"
import { ReactNode } from "react"
import Header from "./Header"
import Footer from "./Footer"

type LayoutProps = {
  children: ReactNode
}
const Layout = ({ children }: LayoutProps) => {
  return (
    <Grommet theme={grommet}>
      <Header />
      <Main>{children}</Main>
      <Footer />
    </Grommet>
  )
}
export default Layout
