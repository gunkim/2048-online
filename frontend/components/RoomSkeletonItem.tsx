import { Card, CardContent, Grid, Skeleton, Typography } from "@mui/material"
import React from "react"
const RoomSkeletonItem = () => {
  return (
    <Grid item xs={6}>
      <Card>
        <CardContent>
          <Grid container spacing={2} style={{ marginTop: 0 }}>
            <Grid item xs={3} style={{ maxWidth: 40 }}>
              <Skeleton variant="text" />
            </Grid>
            <Grid item xs={9} style={{ paddingLeft: 10 }}>
              <Skeleton variant="text" />
              <Skeleton variant="text" />
            </Grid>
          </Grid>
        </CardContent>
      </Card>
    </Grid>
  )
}
export default RoomSkeletonItem
