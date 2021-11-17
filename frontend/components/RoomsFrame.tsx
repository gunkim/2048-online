import {
  Card,
  CardContent,
  Grid,
  List,
  ListItem,
  ListItemText,
  Typography
} from "@mui/material"
import React, { ReactNode, useState } from "react"

type RoomsFrameProps = {
  children: ReactNode
}
const RoomsFrame = ({ children }: RoomsFrameProps) => {
  return (
    <Card style={{ marginBottom: "10px" }}>
      <CardContent>
        <Grid container spacing={2} style={{ marginTop: 0 }}>
          {children}
        </Grid>
      </CardContent>
    </Card>
  )
}
export default RoomsFrame
