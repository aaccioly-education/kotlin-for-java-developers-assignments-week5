package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'
 * (or choosing the corresponding run configuration).
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {

    companion object {

        val winningValues = (1..15).toList()

    }

    private val board = createGameBoard<Int?>(4)

    private var emptyCell = board.getCell(board.width, board.width)

    override fun initialize() {
        with(board) {
            getAllCells().zip(initializer.initialPermutation).forEach {
                this[it.first] = it.second
            }
            emptyCell = board.getCell(board.width, board.width)
            set(emptyCell, null)
        }
    }

    override fun canMove() = true

    override fun hasWon(): Boolean {
        return emptyCell.i == board.width && emptyCell.j == board.width
                && board.getAllValues() == winningValues
    }

    override fun processMove(direction: Direction) {
        val directionToMove = direction.reversed()

        with(board) {
            val candidateToSwap = emptyCell.getNeighbour(directionToMove)
            if (candidateToSwap != null) {
                swapValues(emptyCell, candidateToSwap)
                emptyCell = candidateToSwap
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/**
 * List all non null values in the board.
 *
 * @return values for each initialized [board.Cell] that isn't null
 */
fun GameBoard<Int?>.getAllValues() = getAllCells().mapNotNull { get(it) }

/**
 * Swap the values of two cells if there is enough space.
 *
 * For the swap to succeed one of the values should be `null` and the other should not be `null`.
 *
 * @param cell1 the first cell
 * @param cell2 the second cell
 *
 * return `true` if one, and exactly one, of the two cell values is `null`.
 */
fun GameBoard<Int?>.swapValues(cell1: Cell, cell2: Cell): Boolean {
    val cell1Value = get(cell1)
    val cell2Value = get(cell2)

    if ((cell1Value == null).xor(cell2Value == null)) {
        set(cell1, cell2Value)
        set(cell2, cell1Value)

        return true
    }

    return false
}
