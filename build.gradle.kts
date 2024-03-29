import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.50"
}

group = "com.gomezrondon"
version = "1.6.8-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.beryx:text-io:3.3.0")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.spockframework:spock-core:1.2-groovy-2.4")
	testImplementation("org.spockframework:spock-spring:1.2-groovy-2.4")
	//compile("de.jodamob.kotlin:kotlin-runner-spock:0.3.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.1.1.RELEASE")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}



tasks.register<Copy>("copyJar") {
	dependsOn("build")
	from(file("$buildDir/libs/${rootProject.name}-$version.jar"))
	into(file("C:\\temp\\test\\gcp_exam_generator"))
}

tasks.register<Copy>("copyQ") {
	dependsOn("build")
	from(file("/questions"))
	into(file("C:\\temp\\test\\gcp_exam_generator\\questions"))
}