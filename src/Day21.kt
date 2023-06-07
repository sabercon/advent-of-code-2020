fun main() {
    fun buildIngredientAllergenMap(allergenIngredientMap: Map<String, Set<String>>): Map<String, String> {
        if (allergenIngredientMap.isEmpty()) return emptyMap()

        val (allergen, ingredients) = allergenIngredientMap.entries.first { it.value.size == 1 }
        val ingredient = ingredients.single()
        return allergenIngredientMap
            .minus(allergen)
            .mapValues { it.value - ingredient }
            .let { buildIngredientAllergenMap(it) }
            .plus(ingredient to allergen)
    }

    fun buildIngredientAllergenMap(input: List<Pair<List<String>, List<String>>>): Map<String, String> {
        return input.flatMap { (ingredients, allergens) -> allergens.map { it to ingredients.toSet() } }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.reduce { acc, set -> acc intersect set } }
            .let { buildIngredientAllergenMap(it) }
    }

    val input = readLines("Day21").map {
        val (ingredients, allergens) = it.split(" (contains ")
        ingredients.split(" ") to allergens.dropLast(1).split(", ")
    }
    val ingredientAllergenMap = buildIngredientAllergenMap(input)

    input.flatMap { it.first }.count { it !in ingredientAllergenMap }.println()

    ingredientAllergenMap.entries.sortedBy { it.value }.joinToString(",") { it.key }.println()
}
