plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.loom)
    alias(libs.plugins.buildconfig)
}

stonecutter {
    replacements.string {
        direction = eval(current.version, ">=1.21")
        replace(
            "net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket",
            "net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket",
        )
    }
}

val minecraftVersion = stonecutter.current.version.substringBefore('-')

base.archivesName.set("${rootProject.name}-${minecraftVersion}")

repositories {
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")

        content {
            includeGroup("maven.modrinth")
        }
    }
    maven("https://repo.plasmoverse.com/releases")
    maven("https://maven.neoforged.net/")
    maven("https://maven.minecraftforge.net/")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())

    annotationProcessor(libs.lombok)

    implementation(libs.plasmovoice)

    // Use NeoForge as the loader for 1.21.1 builds
    if (stonecutter.current.version.startsWith("1.21.1")) {
        modImplementation("net.neoforged:neoforge:${property("neoforge_version")}")

        // NeoForge/Forge equivalents (where mods are available through Modrinth/Maven)
        modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
        modImplementation("maven.modrinth:plasmo-voice:${property("deps.plasmo_voice_neoforge")}")
        modImplementation("maven.modrinth:replaymod:${property("deps.replaymod")}")
    }
}

buildConfig {
    packageName("su.plo.replayvoice")
    buildConfigField("VERSION", project.version.toString())
}

tasks {
    processResources {
        inputs.property("version", version)

        from("LICENSE")
        filesMatching("fabric.mod.json") {
            expand(mapOf(
                "version" to version,
                "loader_version" to "0.17.0",
                "minecraft_dependency" to project.property("mod.minecraft_dependency"),
                "pv_dependency" to libs.versions.plasmovoice.get(),
                "replaymod_dependency" to "1.16.4-2.6.9" // todo: ???
            ))
        }

        // Generate NeoForge metadata TOML from template placeholders
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf(
                "version" to version,
                "neoforge_version" to project.property("neoforge_version"),
                "minecraft_dependency" to project.property("mod.minecraft_dependency"),
                "pv_dependency" to project.property("deps.plasmo_voice_neoforge"),
                "replaymod_dependency" to project.property("deps.replaymod")
            ))
        }
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(property("deps.java_version").toString().toInt()))
        withSourcesJar()
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${rootProject.name}" }
        }
    }

    val copyToRoot =
        register<Copy>("copyToRoot") {
            from(remapJar.get().archiveFile)
            into(rootProject.layout.buildDirectory.dir("libs"))
        }

    build {
        finalizedBy(copyToRoot)
    }
}
