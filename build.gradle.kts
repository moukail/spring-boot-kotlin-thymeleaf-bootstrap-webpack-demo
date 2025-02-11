plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("com.github.node-gradle.node") version "7.0.2"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "nl.moukafih"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

node {
	download.set(true)
	version.set("20.13.1")
}

val npmRunBuild = tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmRunBuild") {
	args.set(listOf("run", "build"))

	dependsOn(tasks.npmInstall)

	inputs.files(fileTree("node_modules"))
	inputs.files(fileTree("src/main/resources"))
	inputs.file("package.json")
	inputs.file("webpack.config.js")
	outputs.dir(layout.buildDirectory.dir("resources/main/static"))
}

tasks.processResources {
	dependsOn(npmRunBuild)
}