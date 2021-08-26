import { useState } from "react"
import useInterval from "../hooks/useInterval"

const calcProgressTime = (startDate: Date): number => {
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

  return diffMin * 60 + diffSec
}

type TimerProps = {
  startDate: Date | null
}
const Timer = ({ startDate }: TimerProps) => {
  const [count, setCount] = useState(
    startDate ? calcProgressTime(startDate) : null
  )
  const [isRunning, setIsRunning] = useState(true)

  useInterval(
    () => {
      setCount(count + 1)
    },
    isRunning ? 1000 : null
  )
  return <div style={{ textAlign: "center", fontSize: "2rem" }}>{count}</div>
}
export default Timer
