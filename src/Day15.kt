fun main() {
    fun numSeq(seed: List<Int>): Sequence<Triple<Int, Int, Map<Int, Int>>> {
        return generateSequence(Triple(0, -1, mutableMapOf())) { (turn, last, spoken) ->
            val current = when {
                turn < seed.size -> seed[turn]
                last !in spoken -> 0
                else -> turn - spoken[last]!!
            }
            spoken[last] = turn
            Triple(turn + 1, current, spoken)
        }
    }

    val input = readLines("Day15")[0].split(',').map { it.toInt() }

    numSeq(input).elementAt(2020).second.println()
    numSeq(input).elementAt(30000000).second.println()
}
