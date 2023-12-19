package utils.string

fun String.areAllCharsDistinct() = this.length == this.toSet().size
fun String.numOfDistinctChars() = this.toSet().size