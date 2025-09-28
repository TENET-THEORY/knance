plugins { alias(libs.plugins.kotlin.jvm) }

dependencies {
  implementation(projects.account.domain)
  implementation(libs.exposed.core)
  implementation(libs.exposed.dao)
  implementation(libs.exposed.jdbc)
  implementation(libs.postgresql)
}
