package utils

fun String.areAllDistinct() = this.length == this.toSet().size
fun String.numOfDistinct() = this.toSet().size