fun main() {
    data class Rule(val name: String, val range1: IntRange, val range2: IntRange)

    fun validRules(value: Int, rules: Set<Rule>): Set<Rule> {
        return rules.filter { (_, range1, range2) -> value in range1 || value in range2 }.toSet()
    }

    fun buildRuleMap(candidates: List<Pair<Int, Set<Rule>>>): Map<Int, Rule> {
        if (candidates.isEmpty()) return emptyMap()
        val (index, rules) = candidates.minBy { (_, rules) -> rules.size }
        val rule = rules.single()
        return candidates.map { (i, rs) -> i to (rs - rule) }
            .filter { (_, rs) -> rs.isNotEmpty() }
            .let { buildRuleMap(it) }
            .plus(index to rule)
    }

    fun buildRuleMap(rules: Set<Rule>, tickets: List<List<Int>>): Map<Int, Rule> {
        return rules.indices
            .map { it to tickets.fold(rules) { acc, t -> acc intersect validRules(t[it], rules) } }
            .let { buildRuleMap(it) }
    }

    val input = readText("Day16")
    val (rulesStr, myStr, nearbyStr) = """(.+)\n\nyour ticket:\n(.+)\n\nnearby tickets:\n(.+)"""
        .toRegex(RegexOption.DOT_MATCHES_ALL).destructureGroups(input)
    val rules = rulesStr.lines().map {
        val (name, f1, l1, f2, l2) = """(.+): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex().destructureGroups(it)
        Rule(name, f1.toInt()..(l1.toInt()), f2.toInt()..(l2.toInt()))
    }.toSet()
    val myTicket = myStr.split(",").map { it.toInt() }
    val nearbyTickets = nearbyStr.lines().map { line -> line.split(",").map { it.toInt() } }

    nearbyTickets.flatten().filter { validRules(it, rules).isEmpty() }.sum().println()

    val validTickets = nearbyTickets.filter { line -> line.all { validRules(it, rules).isNotEmpty() } }
    val ruleMap = buildRuleMap(rules, validTickets)
    myTicket.filterIndexed { i, _ -> ruleMap[i]!!.name.startsWith("departure") }
        .fold(1L) { acc, v -> acc * v }.println()
}
