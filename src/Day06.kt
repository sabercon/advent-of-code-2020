fun main() {
    fun countAnyYes(group: List<Set<Char>>): Int {
        return group.reduce { acc, set -> acc union set }.size
    }

    fun countAllYes(group: List<Set<Char>>): Int {
        return group.reduce { acc, set -> acc intersect set }.size
    }

    val input = readText("Day06")
        .split(LINE_SEPARATOR + LINE_SEPARATOR)
        .map { it.split(LINE_SEPARATOR).map { ans -> ans.toSet() } }

    input.sumOf { countAnyYes(it) }.println()

    input.sumOf { countAllYes(it) }.println()
}
