plugins {
    // Применяем плагин приложения для поддержки CLI в Java-приложениях
    application
}

repositories {
    // Используем Maven Central для разрешения зависимостей
    mavenCentral()
}

dependencies {
    // Используем JUnit Jupiter для модульного тестирования
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

    // Зависимость используется для приложения
    implementation("com.google.guava:guava:31.1-jre")
}

application {
    // Define the main class for the application.
    mainClass.set("ru.sibsutis.pmik.hmi.interfaces.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
