import { Anchor, Box, Header as GrommetHeader, Nav } from "grommet"

const items = [{ label: "랭킹", href: "#" }]
const Header = () => {
  return (
    <GrommetHeader background="light" pad="medium">
      <Box direction="row" align="center" gap="medium" margin="small">
        <Anchor color="black" href="/">
          이공사팔
        </Anchor>
      </Box>
      <Nav direction="row">
        {items.map(item => (
          <Anchor
            color="black"
            href={item.href}
            label={item.label}
            key={item.label}
          />
        ))}
      </Nav>
    </GrommetHeader>
  )
}
export default Header
