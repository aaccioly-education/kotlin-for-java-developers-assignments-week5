package games.gameOfFifteen


interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized first 15 cells on a board
     * (the last cell is empty)
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    override val initialPermutation by lazy {
        generateSequence { (1..15).shuffled().toList() }.first { candidateValues ->
            isEven(candidateValues) && candidateValues != GameOfFifteen.winningValues }
    }
}

