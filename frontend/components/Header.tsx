import React from "react"
import { Layout } from "antd"
import styled from "styled-components"
const { Header: AntHeader } = Layout

const Logo = styled.h1`
  font-size: 5rem;
  text-align: center;
  padding: 20px 0px;
  span {
    box-shadow: 0px 0px 0px 3px;
    padding: 5px 30px;
    background: #98ff98;
    margin: 5px;
    border-radius: 15px;
  }
`
const Header = () => {
  return (
    <Logo>
      <span style={{ background: "#b8de6f" }}>2</span>
      <span style={{ background: "#f55c47" }}>0</span>
      <span style={{ background: "#f7ea00" }}>4</span>
      <span style={{ background: "#fb9300" }}>8</span>
    </Logo>
  )
}

export default Header
