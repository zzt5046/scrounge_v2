package zjt.projects.models.account

data class AccountSettings(
    private val settings: Map<AccountSetting, String>
){
    fun value() = settings
}

fun defaultAccountSettings(): Map<AccountSetting, String> =
    mapOf(
        AccountSetting.LANGUAGE to Language.ENGLISH.name,
        AccountSetting.UNIT_OF_MEASURE to MeasurementSystem.IMPERIAL.name,
        AccountSetting.THEME to Theme.LIGHT.name
    )

fun Map<AccountSetting, String>.toResponse(): AccountSettingsResponse =
    AccountSettingsResponse(
        settings = this
    )

enum class AccountSetting {
    LANGUAGE,
    THEME,
    UNIT_OF_MEASURE
}

enum class Language {
    ENGLISH,
    SPANISH
}

enum class MeasurementSystem{
    IMPERIAL,
    METRIC,
}

enum class Theme{
    LIGHT,
    TOASTED,
    DARK,
}
