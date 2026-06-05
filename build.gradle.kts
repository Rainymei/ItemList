import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.fabric.loom)
	alias(libs.plugins.kotlin)
	alias(libs.plugins.publish)
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

repositories {
	exclusiveContent {
		forRepository {
			maven("https://maven.teamresourceful.com/repository/maven-public/")
		}
		filter {
			includeGroupByRegex("tech\\.thatgravyboat.*")
			includeGroup("me.owdding")
		}
	}
	exclusiveContent {
		forRepository {
			maven("https://api.modrinth.com/maven")
		}
		filter {
			includeGroup("maven.modrinth")
		}
	}
	exclusiveContent {
		forRepository {
			maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
		}
		filter {
			includeGroup("me.djtheredstoner")
		}
	}
	exclusiveContent {
		forRepository {
			maven("https://repo.hypixel.net/repository/Hypixel/")
		}
		filter {
			includeGroup("net.hypixel")
		}
	}
}

dependencies {
	minecraft(libs.minecraft)

	implementation(libs.fabric.loader)
	implementation(libs.fabric.language.kotlin)

	implementation(libs.fabric.api)
	api(libs.skyblock.api) {
		capabilities {
			requireCapability("tech.thatgravyboat:skyblock-api-26.1")
		}
	}
	include(libs.skyblock.api) {
		capabilities {
			requireCapability("tech.thatgravyboat:skyblock-api-26.1")
		}
	}
}

loom {
	runConfigs["client"].apply {
		ideConfigGenerated(true)
	}

	accessWidenerPath = file("src/main/resources/skyblock-item-list.classtweaker")
}

tasks.processResources {
	inputs.property("version", project.property("version"))

	filesMatching("fabric.mod.json") {
		val props = mapOf(
			"version" to inputs.properties["version"]
		)
		expand(props)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 25
}

kotlin {
	compilerOptions {
		jvmTarget = JvmTarget.JVM_25
	}
}

java {
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

tasks.register<Jar>("apiJar") {
	description = "Assembles a jar with only API classes"
	archiveClassifier.set("api")
	from(sourceSets.main.get().output) {
		include("com/operationpotato/itemlist/api/**")
	}
}

tasks.register<Jar>("apiSourcesJar") {
	description = "Assembles a jar with only API sources"
	archiveClassifier.set("api-sources")
	from(sourceSets.main.get().allSource) {
		include("com/operationpotato/itemlist/api/**")
	}
}

tasks.named("assemble") {
	dependsOn("apiJar", "apiSourcesJar")
}

publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/OperationPotato/ItemList")
			credentials {
				username = System.getenv("MAVEN_USER")
				password = System.getenv("MAVEN_TOKEN")
			}
		}
	}
	publications {
		register<MavenPublication>("gpr") {
			pom {
				name.set("ItemList-API")
				url.set("https://github.com/OperationPotato/ItemList")
				if (System.getenv("IS_FULL_RELEASE") != "true") {
					version += "-SNAPSHOT"
				}
			}
			artifact(tasks.named("apiJar"))
			artifact(tasks.named("apiSourcesJar"))
		}
	}
}
