import React, { ReactNode } from "react"
import { Layout as AntLayout } from "antd"
import Header from "./Header"
import Footer from "./Footer"
const { Content } = AntLayout

type LayoutProps = {
  children: ReactNode
  width?: number
}
const Layout = ({ children, width = 700 }: LayoutProps) => {
  return (
    <AntLayout>
      <Header />
      <Content style={{ padding: "0 50px" }}>
        <AntLayout style={{ padding: "24px 0" }}>
          <Content
            style={{
              padding: "0 24px",
              minHeight: 280,
              width: width,
              margin: "0 auto"
            }}
          >
            {children}
          </Content>
        </AntLayout>
      </Content>
      <Footer />
    </AntLayout>
  )
}

export default Layout
