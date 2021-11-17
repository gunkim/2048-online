import {
  Card,
  CardActionArea,
  CardContent,
  Grid,
  Typography
} from "@mui/material"
import { joinRoom } from "../apis/room"
import { useRouter } from "next/router"

type RoomItemProps = {
  id: number
  title: string
  mode: string
  participant: number
  personnel: number
}

const RoomItem = ({
  id,
  title,
  mode,
  participant,
  personnel
}: RoomItemProps) => {
  const router = useRouter()
  const handleJoin = async (roomId: number) => {
    await joinRoom(roomId).then(() => {
      router.push({
        pathname: "/room",
        query: { roomId: roomId }
      })
    })
  }
  return (
    <Grid item xs={6}>
      <Card>
        <CardActionArea onClick={() => handleJoin(id)}>
          <CardContent>
            <Grid container spacing={2} style={{ marginTop: 0 }}>
              <Grid
                item
                xs={3}
                style={{ borderRight: "1px solid black", maxWidth: 40 }}
              >
                <Typography style={{ maxWidth: 40 }}>{id}</Typography>
              </Grid>
              <Grid item xs={9} style={{ paddingLeft: 10 }}>
                <Typography style={{ fontSize: "1.3rem" }}>
                  {title}{" "}
                  <span style={{ float: "right" }}>
                    {participant}/{personnel}
                  </span>
                </Typography>
                <Typography style={{ fontSize: "0.8rem" }} component="div">
                  {mode}
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
