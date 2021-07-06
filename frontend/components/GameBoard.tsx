import { Col, Row } from "antd"
import { Level } from "../style/Level"
import styled from "styled-components"

const Board = styled(Row)`
  width: 100%;
  padding: 7px;
  border-radius: 5px;
`
const Tile = styled.div`
  background-color: ${props =>
    props.lv != undefined ? Level[`LV${props.lv}`] : Level["LV0"]};
  padding: 5px;
  color: black;
  height: 120px;
  text-align: center;
  border-radius: 15px;
  margin: 7px;
  font-weight: bold;
  font-size: 2rem;
  line-height: 110px;
`

type GameBoardProps = {
  board: number[][]
}
const GameBoard = ({ board }: GameBoardProps) => {
  return (
    <Board>
      {board.map(row =>
        row.map((col, index) => (
          <Col key={index} span={6}>
            <Tile lv={col}>{col != 0 ? Math.pow(2, col) : ""}</Tile>
          </Col>
        ))
      )}
    </Board>
  )
}

export default GameBoard
