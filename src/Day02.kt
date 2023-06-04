fun main() {
    fun isValid1(range: IntRange, letter: Char, password: String): Boolean {
        return password.count { it == letter } in range
    }

    fun isValid2(range: IntRange, letter: Char, password: String): Boolean {
        return (password[range.first - 1] == letter) xor (password[range.last - 1] == letter)
    }

    val input = readLines("Day02").map {
        val (start, end, letter, password) = """(\d+)-(\d+) ([a-z]): ([a-z]+)""".toRegex()
            .matchEntire(it)!!.destructured
        Triple(start.toInt()..end.toInt(), letter.single(), password)
    }

    input.count { (range, letter, password) -> isValid1(range, letter, password) }.println()

    input.count { (range, letter, password) -> isValid2(range, letter, password) }.println()
}
