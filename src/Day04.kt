fun main() {
    fun hasRequiredKeys(passport: Map<String, String>): Boolean {
        return passport.keys.containsAll(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))
    }

    fun hasValidValues(passport: Map<String, String>): Boolean {
        return passport.all { (k, v) ->
            when (k) {
                "byr" -> v.toInt() in 1920..2002
                "iyr" -> v.toInt() in 2010..2020
                "eyr" -> v.toInt() in 2020..2030
                "hgt" -> when {
                    v.endsWith("cm") -> v.dropLast(2).toInt() in 150..193
                    v.endsWith("in") -> v.dropLast(2).toInt() in 59..76
                    else -> false
                }

                "hcl" -> v.matches(Regex("#[0-9a-f]{6}"))
                "ecl" -> v in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "pid" -> v.matches(Regex("[0-9]{9}"))
                else -> true
            }
        }
    }

    val input = readText("Day04")
        .split("\n\n")
        .map {
            it.split(' ', '\n').associate { kv ->
                val (k, v) = kv.split(':')
                k to v
            }
        }

    input.count { hasRequiredKeys(it) }.println()

    input.count { hasRequiredKeys(it) && hasValidValues(it) }.println()
}
