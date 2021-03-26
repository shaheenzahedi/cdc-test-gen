package service.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import service.generators.IntegrationTestGenerator
import service.generators.annotations.JAnnotationGenerator
import service.generators.fields.JFieldGenerator
import service.io.FileResource
import service.mapper.JsonMapper

class CDCTestGenApplication : KoinComponent {
    val integrationTestGenerator by inject<IntegrationTestGenerator>()
    val jsonMapper by inject<JsonMapper>()
    val fileResourceModule = module {
        single { FileResource() }
        single { JsonMapper(get() as FileResource) }
    }
    val integrationTestJavaModule = module {
        single { JAnnotationGenerator() }
        single { JFieldGenerator(get() as JAnnotationGenerator) }
        single { IntegrationTestGenerator(get() as JAnnotationGenerator, get() as JFieldGenerator) }
    }
}