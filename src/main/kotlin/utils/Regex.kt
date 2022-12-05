package utils

fun String.getInts(): List<Int> = """\d+""".toRegex().findAll(this).map (MatchResult::value).map(String::toInt).toList()
fun String.getInt(): Int? = """\d+""".toRegex().find(this)?.value?.toInt()
