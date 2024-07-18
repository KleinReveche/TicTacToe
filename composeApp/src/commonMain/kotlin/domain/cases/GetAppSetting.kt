package domain.cases

import domain.repository.AppSettingRepository

class GetAppSetting(
    private val repository: AppSettingRepository
) {
    operator fun invoke(key: String) = repository.getAppSetting(key)
}
