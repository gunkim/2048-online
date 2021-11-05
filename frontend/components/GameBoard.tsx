import { Col, Row } from "antd"
import { Level } from "../style/Level"
import styled from "styled-components"
import { Fragment } from "react"

const Board = styled(Row)`
  padding: 7px;
  border-radius: 5px;
  @media screen and (max-width: 2000px) {
    width: 670px;

    .tile {
      width: 150px;
      height: 150px;
      line-height: 140px;
      font-size: 3rem;
    }
  }
  @media screen and (max-width: 1366px) {
    width: 430px;

    .tile {
      width: 90px;
      height: 90px;
      line-height: 90px;
      font-size: 2rem;
    }
  }
  @media screen and (max-width: 768px) {
    width: 270px;

    .tile {
      width: 50px;
      height: 50px;
      line-height: 40px;
      font-size: 2rem;
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
  border-radius: 10px;
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
              <Tile className="tile" key={index} lv={col}>
                {col != 0 ? Math.pow(2, col) : ""}
              </Tile>
            ))}
          </Fragment>
        )
      })}
    </Board>
  </>
)

export default GameBoard
