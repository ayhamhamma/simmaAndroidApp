

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
//    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    kotlin("plugin.serialization") version "1.5.21"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.simma.simmaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.simma.simmaapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

}
// Allow references to generated code


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
//    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.46")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt ("com.google.dagger:hilt-android-compiler:2.46")



    // Rerofit // okHttp // Interceptor
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // glide compose
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    // navigation
    implementation ("androidx.compose.ui:ui:1.6.0")
    implementation ("androidx.compose.material:material:1.6.0")
    implementation ("androidx.navigation:navigation-compose:2.7.6")
    implementation("com.airbnb.android:lottie-compose:4.0.0")
    implementation ("com.github.BILLyTheLiTTle:LazyColumns:0.3.7")
    // biometric
    implementation ("androidx.biometric:biometric:1.1.0")

    // kochava
    implementation("com.kochava.tracker:tracker:5.3.0")
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation ("com.android.installreferrer:installreferrer:2.2")
    implementation ("com.google.android.gms:play-services-appset:16.0.2")

    // UXCam
    implementation ("com.uxcam:uxcam:3.6.21")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-analytics")

    // constrainLayout
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")
    // To use constraintlayout in compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    // circular image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // firebase messaging
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation ("com.google.firebase:firebase-messaging-directboot:23.4.1")

    // tab layout and view pager
    implementation("com.google.accompanist:accompanist-pager:0.21.0-beta")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.21.0-beta")


}
kapt {
    correctErrorTypes = true
}