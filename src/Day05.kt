fun main() {
    fun id(seat: String): Int {
        return seat.replace('F', '0').replace('B', '1')
            .replace('L', '0').replace('R', '1')
            .toInt(2)
    }

    val input = readLines("Day05")

    input.maxOf { id(it) }.println()

    val ids = input.map { id(it) }.toSet()
    (ids.min()..ids.max()).first { it !in ids && (it - 1) in ids && (it + 1) in ids }.println()
}
