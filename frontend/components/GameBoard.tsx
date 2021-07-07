import { Col, Row } from "antd"
import { Level } from "../style/Level"
import styled from "styled-components"

const Board = styled(Row)`
  padding: 7px;
  border-radius: 5px;
`
const Tile = styled.div`
  background-color: ${props =>
    props.lv != undefined ? Level[`LV${props.lv}`] : Level["LV0"]};
  padding: 5px;
  color: black;
  height: 100px;
  width: 100px;
  text-align: center;
  border-radius: 15px;
  margin: 7px;
  font-weight: bold;
  font-size: 2rem;
  line-height: 80px;
`

type GameBoardProps = {
  board: number[][]
}
const GameBoard = ({ board }: GameBoardProps) => {
  return (
    <Board>
      {board.map((row: number[]) =>
        row.map((col: number, index: number) => (
          <Col>
            <Tile lv={col}>{col != 0 ? Math.pow(2, col) : ""}</Tile>
          </Col>
        ))
      )}
    </Board>
  )
}

export default GameBoard
