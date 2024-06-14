package uk.gov.logging

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.LibraryVariant
import com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask
import com.android.build.gradle.tasks.TransformClassesWithAsmTask
import com.android.build.gradle.tasks.factory.AndroidUnitTest
import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.jacoco
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import uk.gov.logging.extensions.ProjectExtensions.libs

val configDir = "${project.rootProject.projectDir}/config"
val jacocoVersion = project.libs.findVersion("jacoco").get().requiredVersion

plugins {
    jacoco
}

jacoco {
    toolVersion = jacocoVersion
}

fun getAsmIntermediate(
    moduleProject: Project,
    variant: String,
    productFlavor: String,
    excludes: List<String>
): Provider<FileTree> {
    return if (moduleProject.tasks.findByName("transform${variant.capitalize()}ClassesWithAsm") != null) {
        moduleProject.tasks.named("transform${variant.capitalize()}ClassesWithAsm")
            .flatMap { asmTask ->
                moduleProject.provider {
                    (asmTask as TransformClassesWithAsmTask).classesOutputDir.asFileTree
                        .matching(PatternSet().exclude(excludes))
                }
            }
    } else if (moduleProject.tasks.findByName("transform${productFlavor.capitalize()}ClassesWithAsm") != null) {
        moduleProject.tasks.named("transform${productFlavor.capitalize()}ClassesWithAsm")
            .flatMap { asmTask ->
                moduleProject.provider {
                    (asmTask as TransformClassesWithAsmTask).classesOutputDir.asFileTree
                        .matching(PatternSet().exclude(excludes))
                }
            }
    } else {
        moduleProject.provider {
            moduleProject.fileTree("$moduleProject.buildDir/intermediates/asm_instrumented_project_classes/$variant/")
                .matching(PatternSet().exclude(excludes))
        }
    }
}

fun getJavacIntermediate(
    moduleProject: Project,
    variant: String,
    productFlavor: String,
    excludes: List<String>
): Provider<FileTree> {
    return when {
        moduleProject.tasks.findByName("compile${variant.capitalize()}JavaWithJavac") != null -> {
            moduleProject.tasks.named("compile${variant.capitalize()}JavaWithJavac")
                .flatMap { javacTask ->
                    moduleProject.provider {
                        (javacTask as JavaCompile).destinationDirectory.asFileTree
                            .matching(PatternSet().exclude(excludes))
                    }
                }
        }

        moduleProject.tasks.findByName("compile${productFlavor.capitalize()}JavaWithJavac") != null -> {
            moduleProject.tasks.named("transform${productFlavor.capitalize()}JavaWithJavac")
                .flatMap { javacTask ->
                    moduleProject.provider {
                        (javacTask as JavaCompile).destinationDirectory.asFileTree
                            .matching(PatternSet().exclude(excludes))
                    }
                }
        }

        else -> {
            moduleProject.provider {
                moduleProject.fileTree("$moduleProject.buildDir/intermediates/javac/$variant/classes")
                    .matching(PatternSet().exclude(excludes))
            }
        }
    }
}

@Suppress("LongMethod")
fun createJacocoReportTask(
    args: Map<String, Any?>
): TaskProvider<JacocoReport> {
    val dependsOn = args["dependsOn"] as Iterable<*>

    val executionDataTree = args["executionData"] as FileTree
    val excludes = args["fileFilters"] as List<String>
    val sourceDirs = args["sourceDirs"] as Any

    val description = args["description"] as String
    val moduleProject = args["module"] as Project
    val productFlavor = args["productFlavor"] as String
    val outputDir = args["outputDir"] as String
    val taskName = args["name"] as String
    val variant = args["variant"] as String

    val asmIntermediateClasses = getAsmIntermediate(
        moduleProject,
        variant,
        productFlavor,
        excludes
    )
    val javacIntermediateClasses = getJavacIntermediate(
        moduleProject,
        variant,
        productFlavor,
        excludes
    )

    val classDirectoriesTree = asmIntermediateClasses.get() + javacIntermediateClasses.get()

    return moduleProject.tasks.register(
        taskName,
        JacocoReport::class.java
    ) {
        this.dependsOn(dependsOn)
        this.description = description
        this.group = "jacoco"

        this.additionalSourceDirs.setFrom(files(sourceDirs))
        this.classDirectories.setFrom(files(classDirectoriesTree))
        this.executionData.setFrom(executionDataTree)
        this.sourceDirectories.setFrom(files(sourceDirs))

        this.reports {
            this.csv.apply {
                required.set(true)
                outputLocation.set(project.file("$outputDir/report.csv"))
            }
            this.xml.apply {
                required.set(true)
                outputLocation.set(project.file("$outputDir/report.xml"))
            }
            this.html.apply {
                required.set(true)
                outputLocation.set(project.file("$outputDir/report.html"))
            }
        }

        this.doLast {
            listOf(
                "Execution Data Files: ${executionDataTree.files.map { it.absolutePath }}",
                "Source Files: ${files(sourceDirs).map { it.absolutePath }}",
                "Class Directories: ${files(classDirectoriesTree).map { it.absolutePath }}",
                "XML Output File: ${file("$outputDir/report.xml").absolutePath}"
            ).forEach { message ->
                logger.lifecycle("JaCoCo Report: $message\n")
            }
        }
    }
}

@Suppress("LongMethod", "MaxLineLength")
fun setupAndroidReporting(moduleProject: Project, variants: List<LibraryVariant>) {
    moduleProject.tasks.withType<Test>().configureEach {
        (this.property("jacoco") as JacocoTaskExtension).isIncludeNoLocationClasses = true
        (this.property("jacoco") as JacocoTaskExtension).excludes = listOf("jdk.internal.*")
    }

    variants.forEach { moduleVariant ->
        if (moduleVariant.buildType.name == "debug") {
            val flavor = moduleVariant.flavorName
            val capitalisedVariantName = moduleVariant.name.capitalize()
            val reportPrefix = "${moduleProject.buildDir}/reports/jacoco/${moduleVariant.name}"

            val kotlinCompileTask = moduleProject.tasks.named(
                "compile${capitalisedVariantName}Kotlin"
            )
            val coverageSourceDirs = kotlinCompileTask.flatMap { kcTask ->
                moduleProject.provider {
                    (kcTask as KotlinCompile).sources.files.map { it.absolutePath }
                }
            }

            val customUnitJacocoReportTaskName = "jacoco${capitalisedVariantName}UnitTestReport"
            val androidUnitJacocoReportTask = moduleProject.tasks.named(
                "create${capitalisedVariantName}UnitTestCoverageReport"
            )
            val unitReportPrefix = "$reportPrefix/unit"
            val unitTestTask = moduleProject.tasks.named("test${capitalisedVariantName}UnitTest")
            val unitTestExecutionDataFile = unitTestTask.flatMap { utTask ->
                moduleProject.provider {
                    (utTask as AndroidUnitTest).jacocoCoverageOutputFile.get().asFile
                        .parentFile
                        .absolutePath
                }
            }

            // build/jacoco folder and build/outputs/unit_test_code_coverage folders
            val unitTestExecutionData: ConfigurableFileTree = moduleProject.fileTree(
                mapOf(
                    "dir" to unitTestExecutionDataFile,
                    "includes" to listOf("${unitTestTask.get().name}.exec")
                )
            )

            val customUnitTestReportTask = createJacocoReportTask(
                mapOf(
                    "dependsOn" to listOf(unitTestTask, androidUnitJacocoReportTask),
                    "description" to "Create coverage report from the '$capitalisedVariantName' unit tests.",
                    "executionData" to unitTestExecutionData,
                    "fileFilters" to Filters.androidUnitTests,
                    "module" to moduleProject,
                    "name" to customUnitJacocoReportTaskName,
                    "outputDir" to unitReportPrefix,
                    "productFlavor" to flavor,
                    "sourceDirs" to coverageSourceDirs,
                    "variant" to moduleVariant.name
                )
            )
            unitTestTask.configure {
                this.finalizedBy(customUnitTestReportTask)
            }

            // TODO
            val customConnectedJacocoReportTaskName =
                "connected${capitalisedVariantName}JacocoTestReport"
            val androidConnectedJacocoReportTask = moduleProject.tasks.named(
                "create${capitalisedVariantName}AndroidTestCoverageReport"
            )
            val connectedReportPrefix = "$reportPrefix/connected"
            val connectedTestTask = moduleProject.tasks.named(
                "connected${capitalisedVariantName}AndroidTest"
            )
            val connectedTestExecutionDirectory = connectedTestTask.flatMap { conTask ->
                moduleProject.provider {
                    val something: DirectoryProperty =
                        (conTask as DeviceProviderInstrumentTestTask).coverageDirectory
                    something.asFile.get().absolutePath
                }
            }
            val connectedTestExecutionData: ConfigurableFileTree = fileTree(
                mapOf(
                    "dir" to connectedTestExecutionDirectory,
                    "includes" to listOf("**/*.ec")
                )
            )

            val customConnectedReportTask = createJacocoReportTask(
                mapOf(
                    "dependsOn" to listOf(
                        connectedTestTask,
                        androidConnectedJacocoReportTask
                    ),
                    "description" to "Create coverage report from the '$capitalisedVariantName' instrumentation tests.",
                    "executionData" to connectedTestExecutionData,
                    "fileFilters" to Filters.androidInstrumentationTests,
                    "module" to moduleProject,
                    "name" to customConnectedJacocoReportTaskName,
                    "outputDir" to connectedReportPrefix,
                    "productFlavor" to flavor,
                    "sourceDirs" to coverageSourceDirs,
                    "variant" to moduleVariant.name
                )
            )
            connectedTestTask.configure {
                this.finalizedBy(customConnectedReportTask)
            }
            val jacocoCombinedTaskName = "combined${capitalisedVariantName}JacocoTestReport"
            val combinedReportPrefix = "$reportPrefix/combined"
            val combinedTestExecutionData: FileTree =
                unitTestExecutionData + connectedTestExecutionData

            createJacocoReportTask(
                mapOf(
                    "dependsOn" to listOf(
                        customConnectedReportTask,
                        customUnitTestReportTask
                    ),
                    "description" to "Create coverage report from the '$capitalisedVariantName' instrumentation tests.",
                    "executionData" to combinedTestExecutionData,
                    "fileFilters" to Filters.androidInstrumentationTests,
                    "module" to moduleProject,
                    "name" to jacocoCombinedTaskName,
                    "outputDir" to combinedReportPrefix,
                    "productFlavor" to flavor,
                    "sourceDirs" to coverageSourceDirs,
                    "variant" to moduleVariant.name
                )
            )
        }
    }
}

project.afterEvaluate {
    setupAndroidReporting(
        project,
        (project.findProperty("android") as LibraryExtension).libraryVariants.toTypedArray().asList()
    )
}
