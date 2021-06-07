export function rotateArr(originalArr, rotateCount) {
  const N = 4
  const rotatedArr = Array.from(Array(N), () => new Array(4))
  rotateCount = rotateCount % 4
  if (rotateCount == 1 || rotateCount == -3) {
    for (let row = 0; row < N; row++) {
      for (let col = 0; col < N; col++) {
        rotatedArr[col][N - 1 - row] = originalArr[row][col]
      }
    }
  } else if (rotateCount == 2 || rotateCount == -2) {
    for (let row = 0; row < N; row++) {
      for (let col = 0; col < N; col++) {
        rotatedArr[N - 1 - row][N - 1 - col] = originalArr[row][col]
      }
    }
  } else if (rotateCount == 3 || rotateCount == -1) {
    for (let row = 0; row < N; row++) {
      for (let col = 0; col < N; col++) {
        rotatedArr[N - 1 - col][row] = originalArr[row][col]
      }
    }
  } else {
    return originalArr
  }
  return rotatedArr
}
export const combineLeft = (board: number[][]) => {
  const version = board.length
  for (let row = 0; row < version; row++) {
    for (let col = 0; col < version - 1; col++) {
      if (board[row][col] === board[row][col + 1]) {
        board[row][col] = board[row][col] + board[row][col + 1]
        board[row][col + 1] = 0
      }
    }
  }
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
