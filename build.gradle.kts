plugins {
    java
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.1.0")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
    implementation("org.hibernate:hibernate-core:6.2.3.Final")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.apache.pdfbox:pdfbox:2.0.27")
    implementation("org.flywaydb:flyway-core:9.19.4")
    implementation("org.flywaydb:flyway-mysql:9.19.4")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
