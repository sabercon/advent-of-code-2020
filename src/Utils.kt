import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

val LINE_SEPARATOR = System.lineSeparator()!!

/**
 * Reads text from the given input txt file.
 */
fun readText(name: String) = File("src", "$name.txt")
    .readText().trim()

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Matches the given line with the regex and returns the destructured groups.
 */
fun Regex.destructureGroups(line: String) = matchEntire(line)!!.destructured

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
