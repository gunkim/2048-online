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
          <Grid item xs={9.5}>
            <Grid
              container
              spacing={2}
              style={{ overflow: "auto", height: 570 }}
            >
              {children}
            </Grid>
          </Grid>
          <Grid item xs={2.5}>
            <Typography variant="h6" component="div">
              접속자
            </Typography>
            <List style={{ overflow: "auto", height: 535 }}>
              <ListItem style={{ padding: "0px 16px" }}>
                <ListItemText style={{ margin: 0 }} primary="인원1" />
              </ListItem>
            </List>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  )
}
export default RoomsFrame
