import {
  Box,
  Button,
  FormControl,
  FormControlLabel,
  FormGroup,
  FormLabel,
  Radio,
  RadioGroup,
  TextField,
  Typography
} from "@mui/material"
import { useRouter } from "next/router"
import React, { useState } from "react"
import { createRoom } from "../apis/room"

const RoomCreateForm = () => {
  const router = useRouter()
  const [form, setForm] = useState({
    title: "",
    personnel: "TWO",
    mode: "TIME_ATTACK"
  })

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
    <Box
      sx={{
        position: "absolute" as "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        width: 400,
        bgcolor: "background.paper",
        border: "2px solid #000",
        boxShadow: 24,
        p: 4
      }}
    >
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
            <FormControlLabel value="FOUR" control={<Radio />} label="4명" />
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
  )
}
export default RoomCreateForm
