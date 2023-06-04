fun main() {
    fun parse(line: String): Pair<String, List<Pair<Int, String>>> {
        val (outer, inner) = """([a-z ]+) bags contain ([0-9a-z, ]+)\.""".toRegex().destructureGroups(line)
        val inners = inner.split(", ").mapNotNull {
            if (it == "no other bags") return@mapNotNull null

            val (count, color) = """(\d+) ([a-z ]+) bags?""".toRegex().destructureGroups(it)
            count.toInt() to color
        }
        return outer to inners
    }

    fun countOuters(rules: Map<String, List<Pair<Int, String>>>, color: String): Int {
        val map = rules.flatMap { (outer, inners) -> inners.map { (_, inner) -> inner to outer } }
            .groupBy({ it.first }, { it.second })

        fun outers(color: String): Set<String> {
            if (color !in map) return emptySet()
            return map[color]!!.fold(emptySet()) { acc, c -> acc + c + outers(c) }
        }

        return outers(color).size
    }

    fun countInners(rules: Map<String, List<Pair<Int, String>>>, color: String): Int {
        if (color !in rules) return 0
        return rules[color]!!.sumOf { (count, color) -> count * (1 + countInners(rules, color)) }
    }


    val input = readLines("Day07").associate { parse(it) }

    countOuters(input, "shiny gold").println()

    countInners(input, "shiny gold").println()
}
