package zjt.projects.models.account

data class AccountSettingsResponse (
    val settings: Map<AccountSetting, String>
)