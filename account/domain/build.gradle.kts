plugins { alias(libs.plugins.kotlin.jvm) }

dependencies {
  api(libs.kotlinx.coroutines.core)
  implementation(projects.marketData.domain)
}
