fun main() {
    fun index(moves: List<String>): Pair<Int, Int> {
        return moves.fold(0 to 0) { (i, j), c ->
            when (c) {
                "e" -> i + 2 to j
                "w" -> i - 2 to j
                "se" -> i + 1 to j - 1
                "sw" -> i - 1 to j - 1
                "ne" -> i + 1 to j + 1
                "nw" -> i - 1 to j + 1
                else -> error("Unknown direction $c")
            }
        }
    }

    fun neighbours(i: Int, j: Int): List<Pair<Int, Int>> {
        return listOf(
            i + 2 to j,
            i - 2 to j,
            i + 1 to j - 1,
            i - 1 to j - 1,
            i + 1 to j + 1,
            i - 1 to j + 1,
        )
    }

    fun flip(blackTiles: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        return blackTiles.flatMap { (i, j) -> neighbours(i, j) + (i to j) }
            .filter { (i, j) ->
                val blackNeighbours = neighbours(i, j).count { blackTiles.contains(it) }
                if (i to j in blackTiles) blackNeighbours in 1..2 else blackNeighbours == 2
            }
            .toSet()
    }

    val input = readLines("Day24")
        .map { line ->
            line.fold(emptyList<String>()) { acc, c ->
                when (acc.lastOrNull()) {
                    "s", "n" -> acc.dropLast(1) + (acc.last() + c)
                    else -> acc + c.toString()
                }
            }.let { index(it) }
        }
        .groupBy { it }
        .filterValues { it.size % 2 == 1 }
        .keys

    input.size.println()

    generateSequence(input) { flip(it) }
        .elementAt(100)
        .size.println()
}
