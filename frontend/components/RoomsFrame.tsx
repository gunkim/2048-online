import {
  Box,
  Button,
  Card,
  CardContent,
  FormControl,
  FormControlLabel,
  FormGroup,
  FormLabel,
  Grid,
  List,
  ListItem,
  ListItemText,
  Modal,
  Radio,
  RadioGroup,
  Tab,
  Tabs,
  TextField,
  Typography
} from "@mui/material"
import React, { ReactNode, useState } from "react"
import { createRoom } from "../apis/room"
import { useRouter } from "next/router"

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4
}

type RoomsFrameProps = {
  children: ReactNode
}
const RoomsFrame = ({ children }: RoomsFrameProps) => {
  const router = useRouter()
  const [value, setValue] = useState(null)
  const [open, setOpen] = useState(false)
  const [form, setForm] = useState({
    title: "",
    personnel: "TWO",
    mode: "TIME_ATTACK"
  })

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue)
  }
  const handleOpen = () => setOpen(true)
  const handleClose = () => setOpen(false)
  const handleFormChange = e => {
    const name = e.target.name
    const value = e.target.value

    setForm({
      ...form,
      [name]: value
    })
  }
  const handleFormSubmit = async () => {
    const roomId = await createRoom(form)
    router.push({
      pathname: "/room",
      query: { roomId: roomId }
    })
  }
  return (
    <>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h5" component="h2">
            방 만들기
          </Typography>
          <Box sx={{ width: "100%" }}>
            <FormControl
              component="fieldset"
              style={{ width: "100%" }}
              onChange={handleFormChange}
            >
              <FormLabel
                component="legend"
                style={{ marginTop: 8, marginBottom: 0 }}
              >
                방제
              </FormLabel>
              <FormGroup>
                <TextField
                  id="standard-basic"
                  variant="standard"
                  name="title"
                  placeholder="제목을 입력해주세요"
                  fullWidth
                  value={form.title}
                />
              </FormGroup>
              <FormLabel
                component="legend"
                style={{ marginTop: 8, marginBottom: 0 }}
              >
                인원수
              </FormLabel>
              <RadioGroup
                row
                aria-label="mode"
                name="personnel"
                value={form.personnel}
              >
                <FormControlLabel value="TWO" control={<Radio />} label="2명" />
                <FormControlLabel
                  value="FOUR"
                  control={<Radio />}
                  label="4명"
                />
              </RadioGroup>
              <FormLabel
                component="legend"
                style={{ marginTop: 8, marginBottom: 0 }}
              >
                게임 모드
              </FormLabel>
              <RadioGroup row aria-label="mode" name="mode" value={form.mode}>
                <FormControlLabel
                  value="TIME_ATTACK"
                  control={<Radio />}
                  label="타임어택"
                />
                <FormControlLabel
                  disabled
                  value="SPEED_ATTACK"
                  control={<Radio />}
                  label="스피드어택"
                />
                <FormControlLabel
                  disabled
                  value="SURVIVAL"
                  control={<Radio />}
                  label="서바이벌"
                />
              </RadioGroup>
              <Button variant="contained" fullWidth onClick={handleFormSubmit}>
                방 만들기
              </Button>
            </FormControl>
          </Box>
        </Box>
      </Modal>
      <Box sx={{ width: "100%" }}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <Tabs
            value={value}
            onChange={handleChange}
            aria-label="basic tabs example"
          >
            <Tab
              label="방 만들기"
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#aeadff",
                fontWeight: "bold",
                color: "black"
              }}
              onClick={handleOpen}
            />
            <Tab
              label="랭킹"
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#e38a8a",
                fontWeight: "bold",
                color: "black"
              }}
            />
            <Tab
              label="싱글"
              style={{
                borderRadius: "10px 10px 0px 0px",
                background: "#a5ffa4",
                fontWeight: "bold",
                color: "black"
              }}
            />
          </Tabs>
        </Box>
      </Box>
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
    </>
  )
}
export default RoomsFrame
