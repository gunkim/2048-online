import React from "react"
import { Layout } from "antd"
import { GithubOutlined } from "@ant-design/icons"
const { Footer: AntFooter } = Layout

const Footer = () => {
  return (
    <AntFooter style={{ textAlign: "center" }}>
      2048 Online Game{"    "}
      <a
        style={{ color: "black" }}
        onClick={() => (location.href = "https://github.com/gunkims")}
      >
        <GithubOutlined />
      </a>
    </AntFooter>
  )
}

export default Footer
