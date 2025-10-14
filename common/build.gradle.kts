plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.npm.publish)
}

version = "0.0.1"

kotlin {
  compilerOptions { optIn.add("kotlin.js.ExperimentalJsExport") }

  jvm()

  js {
    outputModuleName = "@common/api"
    browser()
    binaries.library()
    generateTypeScriptDefinitions()
    compilerOptions { target = "es2015" }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.ktor.client.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.json)
      }
    }

    val jvmMain by getting { dependencies { api(libs.ktor.client.apache) } }
  }
}

npmPublish {
  packages {
    named("js") {
      packageJson {
        name = "knance-common-api"
        version = "0.0.1"
      }
    }
  }
  registries {
    npmjs {
      authToken = System.getenv("NPM_TOKEN")
    }
  }
}