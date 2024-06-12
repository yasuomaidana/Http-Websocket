package example.micronaut.graphql

import graphql.language.FieldDefinition
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import io.micronaut.core.io.ResourceResolver
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader

@MicronautTest(transactional = false)
class GraphQLFactoryTest{
    @Inject
    private lateinit var resourceResolver: ResourceResolver

    @Inject
    private lateinit var graphQLFetcherLoader: GraphQLFetcherLoader

    @Test
    fun testAllFetchersImplemented() {
        val schemaParser = SchemaParser()
        val typeRegistry = TypeDefinitionRegistry()
        typeRegistry.merge(schemaParser.parse(
            BufferedReader(
                InputStreamReader(
                resourceResolver.getResourceAsStream("classpath:schema.graphqls").get())
            )
        ))

    val queryType = typeRegistry.getType("Query").get()
    val mutationType = typeRegistry.getType("Mutation").get()

    val queryFields = queryType.children.filterIsInstance<FieldDefinition>()
    val mutationFields = mutationType.children.filterIsInstance<FieldDefinition>()



    queryFields.forEach { field ->
        assertTrue(graphQLFetcherLoader.queryDict.containsKey(field.name), "Missing query fetcher for field ${field.name}")
    }

    mutationFields.forEach { field ->
        assertTrue(graphQLFetcherLoader.mutationDict.containsKey(field.name), "Missing mutation fetcher for field ${field.name}")
    }
}
}