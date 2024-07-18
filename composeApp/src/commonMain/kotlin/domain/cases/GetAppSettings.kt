package domain.cases

import domain.repository.AppSettingRepository

class GetAppSettings(private val repository: AppSettingRepository) {
  operator fun invoke() = repository.getAppSettings()
}
