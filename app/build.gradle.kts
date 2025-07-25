plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.hoshi.pwd"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hoshi.pwd"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets.getByName("main") {
        java.srcDirs("src/main/kotlin")
    }

    buildFeatures { // Enables Jetpack Compose for this module
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8" // 对应 kotlin 版本 1.9.22
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")
    implementation("androidx.startup:startup-runtime:1.2.0") // 统一处理初始化

    implementation("com.google.code.gson:gson:2.11.0")

    // compose 相关依赖
    val composeVersion = "1.8.2"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.material3:material3-android:1.3.2")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Room components
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // 其他好用的第三方库
    implementation("com.github.EndeRHoshI:HoshiCore:1.0.1") // Hoshi 核心
    implementation("com.github.li-xiaojun:XPopup:2.9.19")
    implementation("com.github.getActivity:ToastUtils:10.5")
}