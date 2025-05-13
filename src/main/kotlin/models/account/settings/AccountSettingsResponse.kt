package zjt.projects.models.account.settings

data class AccountSettingsResponse (
    val settings: Map<AccountSetting, String>
)