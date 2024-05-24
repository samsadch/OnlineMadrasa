plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.onlinemadrasa"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file("/Users/samsadcv/Desktop/OnlineMadrasRevamp/OnlineMadrasa/key/Untitled")
            storePassword = "samsadandroid"
            keyAlias = "samsadandroid"
            keyPassword = "samsadandroid"
        }
    }

    defaultConfig {
        applicationId = "com.onlinemadrasa"
        minSdk = 24
        targetSdk = 34
        versionCode = 50
        versionName = "2.1"

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndkVersion = "21.1.6352462"

        signingConfig =  signingConfigs.getByName("release")

    }

    packaging {
        resources.excludes.add("META-INF/DEPENDENCIES")
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.10.2"
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("com.github.barteksc:android-pdf-viewer:2.8.2") {
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation(files("libs/YouTubeAndroidPlayerApi.jar"))


    /*
    * implementation("com.example.library") {
        exclude(group = "com.android.support", module = "support-compat")
    }
    * */

    implementation(libs.androidx.core.ktx){
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.androidx.navigation.fragment)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hud.progress)
    implementation(libs.ios.dialog)
    implementation(libs.glide)


    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation(libs.firebase.database.ktx){
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation(libs.firebase.messaging){
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation("com.google.firebase:firebase-messaging-ktx"){
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation("com.google.firebase:firebase-crashlytics"){
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation("com.google.firebase:firebase-analytics"){
        exclude(group = "com.android.support", module = "support-compat")
    }


    implementation("com.google.apis:google-api-services-youtube:v3-rev99-1.17.0-rc")
    implementation("com.google.http-client:google-http-client-android:1.38.0")
    implementation("com.google.api-client:google-api-client-android:1.31.1")
    implementation("com.google.api-client:google-api-client-gson:1.31.1")


}