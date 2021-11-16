import {
  Card,
  CardActionArea,
  CardContent,
  Grid,
  Typography
} from "@mui/material"
const RoomItem = () => {
  return (
    <Grid item xs={6}>
      <Card>
        <CardActionArea>
          <CardContent>
            <Grid container spacing={2} style={{ marginTop: 0 }}>
              <Grid
                item
                xs={3}
                style={{ borderRight: "1px solid black", maxWidth: 40 }}
              >
                <Typography style={{ maxWidth: 40 }}>3</Typography>
              </Grid>
              <Grid item xs={9} style={{ paddingLeft: 10 }}>
                <Typography style={{ fontSize: "1.3rem" }}>
                  안녕하세요 <span style={{ float: "right" }}>1/4</span>
                </Typography>
                <Typography style={{ fontSize: "0.8rem" }} component="div">
                  스피드
                </Typography>
              </Grid>
            </Grid>
          </CardContent>
        </CardActionArea>
      </Card>
    </Grid>
  )
}
export default RoomItem
