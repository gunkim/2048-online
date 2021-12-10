import {
    Grid,
    styled,
} from "@mui/material"
import React, {ReactNode, useState} from "react"

const MyGrid = styled(Grid)`
  margin-bottom: 10px;
`

type RoomsFrameProps = {
    children: ReactNode
}
const RoomsFrame = ({children}: RoomsFrameProps) => {
    return (
        <MyGrid container spacing={1} style={{marginTop: 0, minHeight: '600px', borderRadius: '11px', background: 'white'}}>
            {children}
        </MyGrid>
    )
}
export default RoomsFrame
