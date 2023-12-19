plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.vc.findpairs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vc.findpairs"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Hilt&Dagger
    val hiltDaggerVersion = "2.49"
    implementation("com.google.dagger:hilt-android:$hiltDaggerVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltDaggerVersion")


    //Room
    val roomVersion = "2.6.1"
    implementation ("androidx.room:room-runtime:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")

    //Coroutines
    val coroutinesVersion = "1.7.3"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    //Coroutines Lifecycle Scopes
    val coroutineLifeCycleVersion = "2.6.2"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$coroutineLifeCycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$coroutineLifeCycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$coroutineLifeCycleVersion")
}