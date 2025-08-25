package zjt.projects.models.account.settings

data class AccountSettings(
    private val settings: Map<AccountSetting, String>
){
    fun value() = settings
}

fun getAllSettings(): Map<AccountSetting, List<String>> =
    mapOf(
        AccountSetting.LANGUAGE to Language.entries.map { it.name },
        AccountSetting.MEASUREMENT_SYSTEM to MeasurementSystem.entries.map { it.name },
        AccountSetting.THEME to Theme.entries.map { it.name }
    )

fun defaultAccountSettings(): Map<AccountSetting, String> =
    mapOf(
        AccountSetting.LANGUAGE to Language.ENGLISH.name,
        AccountSetting.MEASUREMENT_SYSTEM to MeasurementSystem.IMPERIAL.name,
        AccountSetting.THEME to Theme.LIGHT.name
    )

fun Map<AccountSetting, String>.toResponse(): AccountSettingsResponse =
    AccountSettingsResponse(
        settings = this
    )

enum class AccountSetting {
    LANGUAGE,
    THEME,
    MEASUREMENT_SYSTEM
}

enum class Language {
    ENGLISH,
    SPANISH
}

enum class MeasurementSystem{
    IMPERIAL,
    METRIC,
    UNIVERSAL
}

enum class Theme{
    LIGHT,
    TOASTED,
    DARK,
}
