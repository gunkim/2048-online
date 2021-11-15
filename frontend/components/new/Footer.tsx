import { Footer as GrommetFooter, Text } from "grommet"
import { Github } from "grommet-icons"
import React from "react"
const Footer = () => {
  return (
    <GrommetFooter
      background="light-3"
      justify="center"
      pad="small"
      margin="medium"
    >
      <Text textAlign="center" size="small">
        <div>2048 Online Game</div>
        <a
          style={{ color: "black" }}
          onClick={() => (location.href = "https://github.com/gunkims")}
        >
          <Github />
        </a>
      </Text>
    </GrommetFooter>
  )
}

export default Footer
