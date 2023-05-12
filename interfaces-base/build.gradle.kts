plugins {
    // Поддержка CLI в Java-приложениях
    application
    // Упрощение работы с модульной системой Java 9+
    id("org.javamodularity.moduleplugin") version "1.5.0"
    // Упрощение работы с библиотекой JavaFX
    id("org.openjfx.javafxplugin") version "0.0.13"
    // Сборка модулей в образ
    id("org.beryx.jlink") version "2.25.0"
}

group = "ru.sibsutis.pmik.hmi"
version = "1.0.0"
description = "Программный комплекс для изучения правил построения интерфейсов"

val interfacesMainClass = "$group.${rootProject.name}.GUIStarter"

repositories {
    // Для ускорения процесса сборки приоритетно используем локальный репозиторий
    mavenLocal()
    // Используем Maven Central для разрешения зависимостей
    mavenCentral()
}

dependencies {
    // Используем JUnit Jupiter для модульного тестирования
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    // Используем Mockito для модульного тестирования программных компонентов
    testImplementation("org.mockito:mockito-core:5.2.0")
    // Используем TestFX для модульного тестирования визуальных компонентов
    testImplementation("org.testfx:testfx-core:4.0.16-alpha")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
    // Используем Awaitility для асинхронных тестов
    testImplementation("org.awaitility:awaitility:4.2.0")
    // Используем Hamcrest для тестов
    testImplementation("org.hamcrest:hamcrest-core:2.2")

    if (project.hasProperty("headless")) {
        // Используем Monocle для поддержки режима тестирования headless
        testImplementation("org.testfx:openjfx-monocle:jdk-12.0.1+2")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    // Определяем имя модуля приложения
    mainModule.set(rootProject.name)
    // Определяем основной класс приложения
    mainClass.set(interfacesMainClass)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Дополнительные параметры JVM для модульного тестирования
val testPatchArgs = mutableListOf(
        // Добавляем права доступа для TestFX
        "--add-opens", "${rootProject.name}/ru.sibsutis.pmik.hmi.interfaces.windows=org.testfx.junit5",
        "--add-opens", "${rootProject.name}/ru.sibsutis.pmik.hmi.interfaces.forms=org.testfx.junit5",
        "--add-exports", "javafx.graphics/com.sun.javafx.application=org.testfx",
        "--add-reads", "${rootProject.name}=org.testfx",
        // Добавляем права доступа для Awaitility
        "--add-reads", "${rootProject.name}=awaitility"
)

if (project.hasProperty("headless")) {
    // Добавляем системные свойства для включения режима headless
    testPatchArgs.add("-Dtestfx.robot=glass")
    testPatchArgs.add("-Dglass.platform=Monocle")
    testPatchArgs.add("-Dmonocle.platform=Headless")
    testPatchArgs.add("-Dprism.order=sw")
    testPatchArgs.add("-Dprism.verbose=true")

    // Добавляем права доступа для Monocle
    testPatchArgs.add("--add-exports")
    testPatchArgs.add("javafx.graphics/com.sun.glass.ui=org.testfx.monocle")
}

tasks.test {
    // Используем платформу JUnit для модульных тестов
    useJUnitPlatform()

    // Добавляем дополнительные параметры JVM для модульного тестирования
    jvmArgs(testPatchArgs)
}

jlink {
    options.set(listOf(
            "--compress", "2",
            "--no-header-files",
            "--no-man-pages",
            "--verbose"
    ))
    launcher {
        name = "interfaces"
        mainClass.set(interfacesMainClass)
    }
    jpackage {
        imageOptions = listOf(
                "--icon", "src/main/resources/icons/interfaces.ico"
        )
        installerOptions = listOf(
                "--win-dir-chooser",
                "--win-menu",
                "--win-shortcut",
                "--win-shortcut-prompt",
                "--vendor", "SibSUTIs",
                "--description", "Program set for interfaces' making rules learning",
                "--copyright", "SibSUTIs",
                "--verbose"
        )
        installerType = "exe"
    }
}