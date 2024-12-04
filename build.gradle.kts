import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.jetbrains.kotlin.jvm").version("2.0.21")
  id("com.google.cloud.tools.jib") version "3.4.4"
  application
}

repositories {
  jcenter()
}

dependencies {  // <1>
  implementation("org.jetbrains.kotlin:kotlin-stdlib")
  implementation("io.vertx:vertx-web:5.0.0.CR2")
  implementation("org.asciidoctor:asciidoctorj:2.5.13")
}

application {  // <2>
  mainClass = "io.vertx.howtos.knative.serving.AppKt"
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

jib {  // <3>
  to {
    image = "dev.local/jponge/knative-vertx-asciidoctor"
    tags = setOf("v1")
  }
  container {
    mainClass = "io.vertx.howtos.knative.serving.AppKt"
    ports = listOf("8080")
  }
}
