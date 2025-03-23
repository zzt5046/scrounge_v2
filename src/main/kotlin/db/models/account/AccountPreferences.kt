package zjt.projects.db.models.account

data class AccountPreferences(
    private val preferences: Map<AccountPreference, String>
){
    fun value() = preferences
}

fun defaultAccountPreferences(): Map<AccountPreference, String> =
    mapOf(
        AccountPreference.LANGUAGE to Language.ENGLISH.name,
        AccountPreference.UNIT_OF_MEASURE to UnitOfMeasure.IMPERIAL.name,
        AccountPreference.THEME to Theme.LIGHT.name
    )

enum class AccountPreference {
    LANGUAGE,
    UNIT_OF_MEASURE,
    THEME
}

enum class Language {
    ENGLISH,
    SPANISH
}

enum class UnitOfMeasure{
    METRIC,
    IMPERIAL
}

enum class Theme{
    LIGHT,
    DARK,
    TOASTED
}
