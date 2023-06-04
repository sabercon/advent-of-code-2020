fun main() {
    fun trees(lines: List<String>, right: Int, down: Int): Int {
        return lines.withIndex().count { (index, line) ->
            index % down == 0 && line[index / down * right % line.length] == '#'
        }
    }

    val input = readInput("Day03")

    trees(input, 3, 1).println()

    listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        .map { (r, d) -> trees(input, r, d) }
        .reduce { acc, i -> acc * i }
        .println()
}
