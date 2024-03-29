package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list (by removing nulls) and merges equal elements.
 * The parameter 'double' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the result list instead of two merged elements.
 *
 * If the function double("a") returns "aa",
 * then the function moveAndMergeEqual transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
*/
fun <T : Any> List<T?>.moveAndMergeEqual(double: (T) -> T): List<T> {
    val clean = this.filterNotNull()
    val merged = mutableListOf<T>()

    var last = clean.firstOrNull()

    for (i in 1 until clean.size) {
        val current = clean[i]
        if (current == last) {
            merged += double(current)
            last = null
        } else {
            if (last != null) {
                merged += last
            }
            last = current
        }
    }

    if (last != null) {
        merged += last
    }

    return merged
}


