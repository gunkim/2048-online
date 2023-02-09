dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":infra"))

    implementation("org.springframework.boot:spring-boot-starter-websocket:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-mustache:3.0.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.0.1")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
}
