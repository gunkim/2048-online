import { Grommet, Main } from "grommet"
import { grommet } from "grommet/themes"
import React, { ReactNode } from "react"
import Header from "./Header"
import Footer from "./Footer"
import { Container } from "@mui/material"

type LayoutProps = {
  children: ReactNode
}
const Layout = ({ children }: LayoutProps) => {
  return (
    <Grommet theme={grommet}>
      <Header />
      <Main>
        <Container maxWidth="md">{children}</Container>
      </Main>
      <Footer />
    </Grommet>
  )
}
export default Layout
