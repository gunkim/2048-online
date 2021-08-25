import { useState } from "react"

const calcProgressTim = (startDate: Date): string => {
  const endDate: Date = new Date()
  const timeGap: Date = new Date(
    0,
    0,
    0,
    0,
    0,
    0,
    endDate.valueOf() - startDate.valueOf()
  )
  const diffMin: number = timeGap.getMinutes()
  const diffSec: number = timeGap.getSeconds()

  return `${diffMin}:${diffSec}`
}

type TimerProps = {
  startDate: Date
}
const Timer = ({ startDate }: TimerProps) => {
  const [time, setTime] = useState("0:0")

  if (!startDate) {
    return <></>
  }

  setInterval(() => {
    setTime(calcProgressTim(startDate))
  }, 1000)

  return <div style={{ textAlign: "center", fontSize: "2rem" }}>{time}</div>
}
export default Timer
