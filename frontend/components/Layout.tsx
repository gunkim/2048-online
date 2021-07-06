import React from "react"
import { Layout as AntLayout } from "antd"
import Header from "./Header"
import Footer from "./Footer"
const { Content } = AntLayout

const Layout = ({ children }) => {
  return (
    <AntLayout>
      <Header />
      <Content style={{ padding: "0 50px" }}>
        <AntLayout style={{ padding: "24px 0" }}>
          <Content
            style={{
              padding: "0 24px",
              minHeight: 280,
              minWidth: 700,
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
