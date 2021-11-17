import { Anchor, Box, Header as GrommetHeader, Nav } from "grommet"
import React, { useEffect, useState } from "react"
import { getUsername } from "../../util/jwt-util"
import Link from "next/link"

const items = [{ label: "랭킹", href: "#" }]
const Header = () => {
  const [name, setName] = useState("")
  useEffect(() => {
    setName(getUsername())
  }, [setName])
  return (
    <GrommetHeader background="light" pad="medium">
      <Box direction="row" align="center" gap="medium" margin="small">
        <Link href="/">
          <Anchor color="black">이공사팔</Anchor>
        </Link>
      </Box>
      <Nav direction="row">
        {name ? (
          <Anchor color="black" label={name} />
        ) : (
          <>
            {items.map(item => (
              <Anchor
                color="black"
                href={item.href}
                label={item.label}
                key={item.label}
              />
            ))}
          </>
        )}
      </Nav>
    </GrommetHeader>
  )
}
export default Header
