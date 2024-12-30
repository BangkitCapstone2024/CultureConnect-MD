plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.googleGmsGoogleServices)
}

android {
    namespace = "app.culturedev.cultureconnect"
    compileSdk = 35

    defaultConfig {
        applicationId = "app.culturedev.cultureconnect"
        minSdk = 28
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String","BASE_URL" , "${project.findProperty("BASE_URL")}")
        buildConfigField("String" , "PREDICT_URL" , "${project.findProperty("PREDICT_URL")}")
        buildConfigField("String" , "MAPS_API_KEY" , "${project.findProperty("MAPS_API_KEY")}")
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

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.translate)

    //VIEWPAGER
    implementation(libs.androidx.viewpager2)
    implementation(libs.dotsindicator)

    //GLIDE
    implementation(libs.glide)

    //RETROFIT
    implementation(libs.retrofit)

    //OKHTTP
    implementation(libs.okhttp)

    //LOGGING INTERCEPTOR
    implementation(libs.logging.interceptor)

    //RETROFIT GSON
    implementation(libs.converter.gson)

//    Data Store
    implementation(libs.androidx.datastore.preferences)

    //RXJAVA2
    implementation(libs.rxjava)
    implementation(libs.rxbinding)
}