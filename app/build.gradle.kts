import org.gradle.internal.impldep.org.apache.maven.model.InputLocation.StringFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.example.pdf_signature"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pdf_signature"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

    }



    android.applicationVariants.all {

        val buildType = this.buildType.name
        var filename: String? = null
        this.outputs.forEach {
            if (buildType == "release") {
                filename = "${defaultConfig.versionName}_${defaultConfig.versionName}_${getCurrentTime()}.apk"
            }
        }
    }
}

fun getCurrentTime(): String {
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    return dateFormat.format(Date())
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.okhttps.gson)
    implementation(libs.gson)
    implementation(libs.androidx.material)
    implementation(libs.hutool.all)
    implementation(libs.androidx.recyclerview)
    implementation(libs.refresh.layout.kernel)
    implementation(libs.refresh.header.material)
    implementation(libs.refresh.header.classics)
    implementation(libs.refresh.footer.classics)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}