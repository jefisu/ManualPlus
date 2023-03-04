plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp") version "1.7.20-1.0.8"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("io.realm.kotlin") version "1.6.0"
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.jefisu.manualplus"
    compileSdk = 33

    applicationVariants.all {
        addJavaSourceFoldersToModel(
                File(buildDir, "generated/ksp/$name/kotlin")
        )
    }

    defaultConfig {
        applicationId = "com.jefisu.manualplus"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Compose dependencies
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Splash API
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Compose destinations
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.7.33-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.7.33-beta")

    // Mongo DB Realm
    implementation("io.realm.kotlin:library-sync:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Message Bar Compose
    implementation("com.github.stevdza-san:MessageBarCompose:1.0.5")

    // One-Tap Compose
    implementation("com.github.stevdza-san:OneTapCompose:1.0.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Firebase
    implementation("com.google.firebase:firebase-storage-ktx:20.1.0")

    // Landscapist
    implementation("com.github.skydoves:landscapist-bom:2.1.4")
    implementation("com.github.skydoves:landscapist-glide")
}