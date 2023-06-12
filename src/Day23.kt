fun main() {
    fun move(current: Int, linkMap: MutableMap<Int, Int>): Int {
        val picked = generateSequence(current) { linkMap[it]!! }
            .drop(1).take(3).toList()
        val destination = generateSequence(current - 1) { it - 1 }
            .map { if (it < 1) linkMap.size + it else it }
            .first { it !in picked }
        linkMap[current] = linkMap[picked.last()]!!
        linkMap[picked.last()] = linkMap[destination]!!
        linkMap[destination] = picked.first()

        return linkMap[current]!!
    }

    fun move(cups: List<Int>, times: Int): Map<Int, Int> {
        val linkMap = cups.zipWithNext().toMap(mutableMapOf())
        linkMap[cups.last()] = cups.first()
        var current = cups.first()
        repeat(times) { current = move(current, linkMap) }
        return linkMap
    }

    val input = readText("Day23").map { it.digitToInt() }

    move(input, 100)
        .let { map -> generateSequence(map[1]!!) { map[it]!! } }
        .takeWhile { it != 1 }
        .joinToString("")
        .println()

    move(input + (input.size + 1..1_000_000), 10_000_000)
        .let { 1L * it[1]!! * it[it[1]!!]!! }
        .println()
}
