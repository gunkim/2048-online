import React, { useState } from "react"

import { Box, Meter } from "grommet"
import useInterval from "./../hooks/useInterval"
import styled from "styled-components"
import { useRouter } from "next/router"

const Logo = styled.div`
  background-image: url("logo.png");
  background-size: 350px 100px;
  width: 350px;
  height: 100px;
  margin-bottom: 20px;
`
const Home = () => {
  const router = useRouter()
  const [value, setValue] = useState(0)
  useInterval(() => {
    if (value == 100) {
      router.push("/branch")
    }
    setValue(value + 1)
  }, 8)
  return (
    <Box
      style={{ height: "100%" }}
      direction="row-responsive"
      justify="center"
      align="center"
    >
      <Box
        style={{ height: "100%" }}
        direction="column"
        justify="center"
        align="center"
      >
        <Logo />
        <Meter size="medium" type="bar" background="light-2" value={value} />
      </Box>
    </Box>
  )
}

export default Home
