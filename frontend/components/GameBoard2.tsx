import { Col, Row } from "antd"
import { Level } from "../style/Level"
import styled from "styled-components"
import { Fragment } from "react"

const Board = styled(Row)`
  padding: 7px;
  border-radius: 5px;
  @media screen and (max-width: 2000px) {
    width: 180px;

    .tile {
      width: 26.5px;
      height: 26.5px;
      line-height: 140px;
      font-size: 3rem;
      border-radius: 10px;
    }
  }
  @media screen and (max-width: 1366px) {
    width:130px;

    .tile {
      width: 15px;
      height: 15px;
      border-radius: 4px;
    }
  }
  margin: 0 auto;
`
const Tile = styled.div`
  background-color: ${props =>
    props.lv != undefined ? Level[`LV${props.lv}`] : Level["LV0"]};
  padding: 5px;
  color: black;
  text-align: center;
  margin: 7px;
  font-weight: bold;
  font-size: 2rem;
  line-height: 40px;
`
const Blind = styled.div`
  position: absolute;
  background-color: rgba(0, 0, 0, 0.65);
  z-index: 10;
  color: white;
  font-weight: bold;
  text-align: center;
  font-size: 2vw;
`

type GameBoardProps = {
  board: number[][]
  over: boolean
}
const GameBoard = ({ board, over }: GameBoardProps) => (
  <>
    {over && <Blind>GAME OVER!!</Blind>}
    <Board>
      {board.map((row: number[], index: number) => {
        return (
          <Fragment key={index}>
            {row.map((col: number, index: number) => (
              <Tile className="tile" key={index} lv={col}/>
            ))}
          </Fragment>
        )
      })}
    </Board>
  </>
)

export default GameBoard
