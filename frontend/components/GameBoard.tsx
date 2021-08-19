import { Col, Row } from "antd"
import { Level } from "../style/Level"
import styled from "styled-components"

const Board = styled(Row)`
  padding: 7px;
  border-radius: 5px;
  width: ${props => `${props.mainWidth}px`};
`
const Tile = styled.div`
  background-color: ${props =>
    props.lv != undefined ? Level[`LV${props.lv}`] : Level["LV0"]};
  padding: 5px;
  color: black;
  width: ${props => `${props.width}px`};
  height: ${props => `${props.height}px`};
  text-align: center;
  border-radius: 10px;
  margin: 7px;
  font-weight: bold;
  font-size: 2rem;
  line-height: ${props => `${props.height - 10}px`};
`
const Blind = styled.div`
  position: absolute;
  background-color: rgba(0, 0, 0, 0.65);
  z-index: 10;
  height: ${props => `${props.mainWidth}px`};
  width: ${props => `${props.mainWidth}px`};
  color: white;
  font-weight: bold;
  text-align: center;
  font-size: 2vw;
  line-height: ${props => `${props.height * 4 + 50}px`};
`

type GameBoardProps = {
  board: number[][]
  mainWidth: number
  width: number
  height: number
  over: boolean
}
const GameBoard = ({
  board,
  mainWidth,
  width,
  height,
  over
}: GameBoardProps) => {
  return (
    <>
      {over && (
        <Blind mainWidth={mainWidth} height={height}>
          GAME OVER!!
        </Blind>
      )}
      <Board mainWidth={`${mainWidth}`}>
        {board.map((row: number[]) => {
          return (
            <>
              {row.map((col: number) => (
                <Tile width={width} height={height} lv={col}>
                  {col != 0 ? Math.pow(2, col) : ""}
                </Tile>
              ))}
            </>
          )
        })}
      </Board>
    </>
  )
}

export default GameBoard
