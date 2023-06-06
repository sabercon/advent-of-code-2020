private fun matchRule(ruleMap: Map<String, List<List<String>>>, message: String, rules: List<List<String>>): List<Int> {
    return rules.flatMap { rs ->
        rs.fold(listOf(0)) { acc, rule ->
            acc.flatMap { size -> matchRule(ruleMap, message.substring(size), rule).map { size + it } }
        }
    }
}

private fun matchRule(ruleMap: Map<String, List<List<String>>>, message: String, rule: String): List<Int> {
    return if (rule.startsWith("\"")) {
        if (message.startsWith(rule[1])) listOf(1) else emptyList()
    } else {
        matchRule(ruleMap, message, ruleMap[rule]!!)
    }
}

fun main() {
    val input = readText("Day19")
    val (rulesStr, messagesStr) = input.split(LINE_SEPARATOR + LINE_SEPARATOR)
    val ruleMap = rulesStr.lines().associate {
        val (id, rawRules) = it.split(": ")
        val rules = rawRules.split(" | ").map { r -> r.split(" ") }
        id to rules
    }
    val messages = messagesStr.lines()

    messages.count { m -> matchRule(ruleMap, m, ruleMap["0"]!!).any { it == m.length } }.println()

    val updatedRuleMap = ruleMap +
            ("8" to listOf(listOf("42"), listOf("42", "8"))) +
            ("11" to listOf(listOf("42", "31"), listOf("42", "11", "31")))
    messages.count { m -> matchRule(updatedRuleMap, m, ruleMap["0"]!!).any { it == m.length } }.println()
}
