import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("de.mannodermaus.android-junit5")
}
val key: String = gradleLocalProperties(rootDir).getProperty("key")

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        buildConfigField("String", "key", key)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraint)
    implementation(AndroidX.lifecycleLiveData)
    implementation(AndroidX.lifecycleViewModel)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConvertor)

    implementation(OKhttp.okHttpInterceptor)
    implementation(OKhttp.okHttpInterceptor)

    implementation(Navigation.navigationKtx)
    implementation(Navigation.navigationFrag)

    implementation(Google.material)
    implementation(Lottie.lottie)
    implementation(Glide.glide)
    kapt(Glide.compiler)

    implementation(Hilt.android)
    kapt(Hilt.compiler)
    implementation("com.google.android.gms:play-services-location:17.0.0")


    testImplementation(UnitTest.jupiter_api)
    testRuntimeOnly(UnitTest.jupiter_engine)
    testImplementation (UnitTest.mock_web_server)
}