package domain.cases

import domain.model.AppSetting
import domain.repository.AppSettingRepository

class UpsertAppSetting(private val repository: AppSettingRepository) {
  suspend operator fun invoke(appSetting: AppSetting) = repository.upsertAppSetting(appSetting)
}
