fun main() {
    fun countArrangements(jolts: List<Int>): Long {
        val cache = mutableMapOf<Int, Long>()
        fun countArrangementsFrom(index: Int): Long {
            if (index == jolts.lastIndex) return 1
            if (index in cache) return cache[index]!!

            return (index + 1..jolts.lastIndex)
                .takeWhile { jolts[it] - jolts[index] <= 3 }
                .sumOf { countArrangementsFrom(it) }
                .also { cache[index] = it }
        }
        return countArrangementsFrom(0)
    }

    val input = readLines("Day10").map { it.toInt() }.sorted()
    val jolts = listOf(0) + input + (input.last() + 3)

    val diffs = jolts.zipWithNext { a, b -> b - a }
    println(diffs.count { it == 1 } * diffs.count { it == 3 })

    countArrangements(jolts).println()
}
