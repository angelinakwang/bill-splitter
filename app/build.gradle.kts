plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.billsplitter"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.billsplitter"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    val appcompat_version = "1.7.0"
    implementation("androidx.appcompat:appcompat:$appcompat_version")
    implementation("androidx.appcompat:appcompat-resources:$appcompat_version")
    implementation("com.google.android.material:material:1.12.0")
    val lifecycle_version = "2.8.3" // Or the latest stable version
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version") // For Kotlin extensions and by viewModels()
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version") // If using Jetpack Compose
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // For ViewModel and other lifecycle-related components, you might also include:
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // Example for ViewModel KTX
    implementation("androidx.fragment:fragment-ktx:1.8.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}