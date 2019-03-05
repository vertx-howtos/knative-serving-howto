import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.jetbrains.kotlin.jvm").version("1.3.20")
  id("com.google.cloud.tools.jib") version "1.0.1"
  application
}

repositories {
  jcenter()
}

dependencies {  // <1>
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("io.vertx:vertx-web:3.6.3")
  implementation("org.asciidoctor:asciidoctorj:1.5.6")
}

application {  // <2>
  mainClassName = "io.vertx.howtos.knative.serving.AppKt"
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

jib {  // <3>
  to {
    image = "dev.local/jponge/knative-vertx-asciidoctor"
    tags = setOf("v1")
  }
  container {
    mainClass = application.mainClassName
    ports = listOf("8080")
  }
}

tasks.wrapper {
  gradleVersion = "5.2.1"
}
