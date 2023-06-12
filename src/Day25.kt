fun main() {
    fun transform(subject: Long): Sequence<Long> {
        return generateSequence(1L) { it * subject % 20201227 }
    }

    val (card, door) = readLines("Day25").map { it.toLong() }
    val loop = transform(7).indexOf(card)
    transform(door).elementAt(loop).println()
}
