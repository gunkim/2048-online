import React, { useEffect, useState } from "react"
import {
  Anchor,
  Box,
  Button,
  Form,
  FormField,
  Grid,
  Heading,
  Layer,
  List,
  RadioButtonGroup,
  Select,
  Text,
  TextInput,
  Tip
} from "grommet"
import Link from "next/link"
import { Notification, Grommet } from "grommet"
import { Gremlin, InProgress, Gamepad, FormClose } from "grommet-icons"
import { createRoom, joinRoom, Room } from "../apis/room"
import { useRouter } from "next/router"
import { useDispatch, useSelector } from "react-redux"
import { getRoomsAsync } from "../store/rooms/actions"
import { RootState } from "../store"

const suggestions = ["같이 즐겁게 게임해요!", "오늘도 즐거운 게임!"]
const TipContent = ({ message }) => (
  <Box direction="row" align="center">
    <svg viewBox="0 0 22 22" version="1.1" width="22px" height="22px">
      <polygon
        fill="grey"
        points="6 2 18 12 6 22"
        transform="matrix(-1 0 0 1 30 0)"
      />
    </svg>
    <Box background="grey" direction="row" pad="small" round="xsmall">
      <Text color="accent-1">{message}</Text>
    </Box>
  </Box>
)
const defaultFormValue = {
  title: "",
  personnel: "TWO",
  mode: "TIME_ATTACK"
}
const Rooms = () => {
  const dispatch = useDispatch()
  const router = useRouter()
  const [open, setOpen] = React.useState(false)
  const [form, setForm] = useState(defaultFormValue)
  useEffect(() => {
    dispatch(getRoomsAsync.request(null, null))
  }, [dispatch])

  const { loading, data, error } = useSelector(
    (state: RootState) => state.rooms.rooms
  )
  const onOpen = () => setOpen(true)
  const onClose = () => {
    setOpen(undefined)
    setForm(defaultFormValue)
  }
  const onCreate = async () => {
    const roomId = await createRoom(form)
    router.push({
      pathname: "/room",
      query: { roomId: roomId }
    })
  }
  const handleJoin = async (roomId: number) => {
    await joinRoom(roomId)
    router.push({
      pathname: "/room",
      query: { roomId: roomId }
    })
  }
  return (
    <Grommet full>
      <Grid
        rows={["medium", "medium"]}
        areas={[["header"], ["main"]]}
        gap="small"
      >
        <Box
          gridArea="header"
          direction="column"
          justify="center"
          align="center"
          background="brand"
          fill
          gap="large"
        >
          <Heading textAlign="center" level="1" size="xsmall" color="white">
            찾으시는 게임이 없으신가요? <br />
            게임을 한번 만들어보세요!
          </Heading>
          <Box>
            <Box fill direction="row" justify="between">
              <Tip
                dropProps={{ align: { left: "right" } }}
                content={<TipContent message="게임 만들기!" />}
                plain
              >
                <Button icon={<Gamepad />} plain={false} onClick={onOpen} />
              </Tip>
            </Box>
          </Box>
        </Box>
        {data && (
          <Box gridArea="main">
            <List
              data={data.slice(0, 10)}
              primaryKey={item => (
                <Text size="large" weight="bold">
                  <a onClick={() => handleJoin(item.id)}>
                    <Anchor>{item.title}</Anchor>
                  </a>
                </Text>
              )}
              secondaryKey={item => (
                <div>
                  <InProgress />
                  <Gremlin /> {item.personnel}/{item.participant}
                  <Notification status="normal" title={""} />
                </div>
              )}
            />
          </Box>
        )}
      </Grid>
      {open && (
        <Layer onClickOutside={onClose} onEsc={onClose}>
          <Box
            pad="medium"
            gap="small"
            width={{ min: "medium" }}
            height={{ min: "small" }}
            fill
          >
            <Box width="medium">
              <Button alignSelf="end" icon={<FormClose />} onClick={onClose} />
              <Form
                value={form}
                onChange={(nextValue, { touched }) => {
                  console.log("Change", nextValue, touched)
                  setForm(nextValue)
                }}
                onReset={() => setForm(defaultFormValue)}
                onSubmit={event =>
                  console.log("Submit", event.value, event.touched)
                }
              >
                <FormField label="제목" name="title">
                  <TextInput
                    name="title"
                    placeholder="제목을 입력해주세요."
                    suggestions={suggestions}
                  />
                </FormField>
                <FormField label="인원수" name="personnel">
                  <RadioButtonGroup
                    name="personnel"
                    options={["TWO", "FOUR"]}
                  />
                </FormField>
                <FormField label="게임모드" name="mode">
                  <Select name="mode" options={["TIME_ATTACK"]} />
                </FormField>
                <Box
                  direction="row"
                  justify="between"
                  margin={{ top: "medium" }}
                >
                  <Button label="Cancel" onClick={onClose} />
                  <Button
                    type="submit"
                    label="Create"
                    primary
                    onClick={onCreate}
                  />
                </Box>
              </Form>
            </Box>
          </Box>
        </Layer>
      )}
    </Grommet>
  )
}

export default Rooms
