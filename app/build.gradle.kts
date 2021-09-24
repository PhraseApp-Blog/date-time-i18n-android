plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

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
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Google.material)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConvertor)

    implementation(OKhttp.okHttpInterceptor)
    implementation(OKhttp.okHttpInterceptor)

    implementation(View.navigationKtx)
    implementation(View.navigationFrag)
    implementation(View.navigationSafeArgs)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    androidTestImplementation(Junit.junit4)
    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(AndroidXTest.espressoCore)
    androidTestImplementation(AndroidXTest.rules)
    androidTestImplementation(AndroidXTest.junitExt)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)
}