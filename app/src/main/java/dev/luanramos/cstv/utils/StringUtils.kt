package dev.luanramos.cstv.utils

fun buildLeagueAndSeriesString(
    leagueName: String,
    seriesName: String,
    seriesFullName: String
): String {
    return when {
        seriesName.isNotEmpty() -> "$leagueName - $seriesName"
        seriesFullName.isNotEmpty() -> "$leagueName - $seriesFullName"
        else -> leagueName
    }
}

fun handlePlayerFullName(
    firstName: String?,
    lastName: String?,
    defaultName: String
): String{
    return if (
        firstName.isNullOrBlank() &&lastName.isNullOrBlank()
    ) {
        defaultName
    } else {
        "${firstName.orEmpty()} ${lastName.orEmpty()}"
    }
}