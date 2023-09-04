plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("shot")
}

android {
    namespace = "com.thananonp.composeexperiment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thananonp.composeexperiment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
        testApplicationId = "com.thananonp.composeexperiment.test"
    }

    shot {
        applicationId = "com.thananonp.composeexperiment"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    /** Compose */
    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.compose.material3:material3")
    // Needed for bottom navigation bar
    implementation("androidx.compose.material:material:1.5.0")
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
//    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
//    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
//    implementation("androidx.compose.material3:material3-window-size-class")
    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.7.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    /** Navigation */
    val navVersion = rootProject.extra.get("navVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")
    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$navVersion")
}