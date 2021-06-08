export const pipe = (...functions: any) => (input: any) => functions.reduce((acc: Function, fn: Function) => fn(acc), input)

export function transposeCCW(board: number[][]): number[][] { 
  // 행렬 돌리기 (시계 반대) 
  let version = board.length || 0 
  let newBoard = Array.from(Array(version), () => Array(version).fill(0)) 
  for (let i = 0; i < version; i++) { 
    newBoard[i] = [] 
    for (let j = 0; j < version; j++) { 
      newBoard[i][j] = board[j][version - i - 1] 
    } 
  } 
  return newBoard 
} 
    
export function transposeCW(board: number[][]): number[][] { 
  // 행렬 돌리기 (시계) 
  let version = board.length || 0 
  let newBoard = Array.from(Array(version), () => Array(version).fill(0)) 
  for (let i = 0; i < version; i++) { 
    newBoard[i] = [] 
    for (let j = 0; j < version; j++) { 
      newBoard[i][version - j - 1] = board[j][i] 
    } 
  } 
  return newBoard 
}

export const combineLeft = (board: number[][]) => { 
  const version = board.length 
  const newBoard = Array.from(board) 
  for (let row = 0; row < version; row++) { 
    for (let col = 0; col < version - 1; col++) { 
      if (newBoard[row][col] === newBoard[row][col + 1]) { 
        newBoard[row][col] = newBoard[row][col] + newBoard[row][col + 1] 
        newBoard[row][col + 1] = 0 
      } 
    } 
  } 
  return newBoard 
}

export const slideRight = (board: number[][]) => {
  const version = board.length
  const newBoard = board.map(row => {
    let remain = row.filter(n => n != 0)
    let zero_cnt = version - remain.length
    let newRow = Array(zero_cnt).fill(0).concat(remain)
    return newRow
  })
  return newBoard
}
export const slideLeft = (board: number[][]) => {
  const version = board.length
  return board.map(row => {
    let remain = row.filter(n => n != 0)
    let zero_cnt = version - remain.length
    let newRow = remain.concat(Array(zero_cnt).fill(0))
    return newRow
  })
}
const isGenerateAvailable = (board: number[][]): boolean => { 
  let check = false 
  board.forEach((row) => 
  row.forEach((n) => { 
    if (n == 0) { 
      check = true 
    } 
  }),) 
  return check 
}
// 같은 보드인지 
export const isSameBoard = (board1: number[][], board2: number[][]): boolean => { 
  return board1.every((row, r) => { return row.every((n, c) => { return board2[r][c] === n }) }) }

export const generateRandom = (board: number[][]): number[][] => { 
  if (!isGenerateAvailable(board)) { 
    return board } 
    const version = board.length 
    let ranNum = Math.floor(Math.random() * version * version) 
    const row = Math.floor(ranNum / version) 
    const column = ranNum % version 
    if (board[row][column] == 0) { board[row][column] = version !== 4 ? version : 2 
      return board } else { return generateRandom(board) } }
export const calScore = (prev: number[][], now: number[][]) => { 
  const Score_prev: any = {} 
  const Score_now: any = {} 
  let score = 0 
  prev.map((row) => { 
    // 이전 보드 숫자들 기록 
    row.map((num) => { Score_prev[num] = Score_prev[num] ? Score_prev[num] + 1 : 1 }) }) 
    now.map((row) => { 
      // 현재 보드 숫자들 기록 
      row.map((num) => { Score_now[num] = Score_now[num] ? Score_now[num] + 1 : 1 }) }) 
      Object.keys(Score_prev).map((num) => { let prev_cnt = Score_prev[num] 
        let now_cnt = Score_now[num] ? Score_now[num] : 0 
        // 터트렸을 때 
        if (prev_cnt > now_cnt) { 
          let isInitiailNum = num === "2" || num === "3" || num === "5" 
          let diff_cnt = isInitiailNum ? prev_cnt - now_cnt + 1 : prev_cnt - now_cnt 
          // 초기값은 랜덤한 위치에 숫자 1개가 새로생긴다 
          score += diff_cnt * parseInt(num) 
          Score_now[parseInt(num) * 2] -= 1 } }) 
          return score }
