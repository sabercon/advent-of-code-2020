import java.io.File

fun template(num: String) = """
    fun main() {
        val input = readLines("Day$num")
        input.println()
    }

""".trimIndent()

fun main() {
    for (i in 1..25) {
        val num = i.toString().padStart(2, '0')
        File("src", "Day$num.txt").createNewFile()
        File("src", "Day$num.kt").run { if (!exists()) writeText(template(num)) }
    }
}
