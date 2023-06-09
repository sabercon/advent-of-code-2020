typealias Deck = List<Int>
typealias Decks = Pair<Deck, Deck>

private fun win1(card1: Int, card2: Int, deck1: Deck, deck2: Deck): Boolean {
    return card1 > card2
}

private fun win2(card1: Int, card2: Int, deck1: Deck, deck2: Deck): Boolean {
    return if (deck1.size < card1 || deck2.size < card2) card1 > card2
    else playToEnd(::win2, deck1.take(card1) to deck2.take(card2)).first

}

private fun play(winFn: (Int, Int, Deck, Deck) -> Boolean, decks: Decks): Decks {
    val card1 = decks.first[0]
    val card2 = decks.second[0]
    val deck1 = decks.first.drop(1)
    val deck2 = decks.second.drop(1)

    return if (winFn(card1, card2, deck1, deck2)) {
        deck1 + card1 + card2 to deck2
    } else {
        deck1 to deck2 + card2 + card1
    }
}

private fun playToEnd(winFn: (Int, Int, Deck, Deck) -> Boolean, decks: Decks, seen: MutableSet<Decks> = mutableSetOf())
        : Pair<Boolean, Deck> {
    val (deck1, deck2) = decks
    if (decks in seen || deck2.isEmpty()) return true to deck1
    if (deck1.isEmpty()) return false to deck2

    seen.add(decks)
    return playToEnd(winFn, play(winFn, decks), seen)
}

fun main() {
    val (deck1, deck2) = readText("Day22").split("\n\n")
        .map { deck -> deck.lines().drop(1).map { it.toInt() } }

    playToEnd(::win1, deck1 to deck2).second
        .reversed().mapIndexed { index, card -> (index + 1) * card }.sum().println()

    playToEnd(::win2, deck1 to deck2).second
        .reversed().mapIndexed { index, card -> (index + 1) * card }.sum().println()
}
