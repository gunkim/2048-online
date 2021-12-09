import {Grommet, Main} from "grommet"
import React, {ReactNode} from "react"
import Header from "./Header"
import Footer from "./Footer"
import {Container} from "@mui/material"

type LayoutProps = {
    children: ReactNode
}
const Layout = ({children}: LayoutProps) => {
    return (
        <>
            <Header/>
            <Main>
                <Container maxWidth="lg">{children}</Container>
            </Main>
        </>
    )
}
export default Layout
