package example.micronaut.graphql

import graphql.GraphQL
import graphql.language.FieldDefinition
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.core.io.ResourceResolver
import jakarta.inject.Singleton
import java.io.BufferedReader
import java.io.InputStreamReader

@Factory
class GraphQLFactory {

    private val logger = org.slf4j.LoggerFactory.getLogger(GraphQLFactory::class.java)

    @Bean
    @Singleton
    fun graphQL(resourceResolver: ResourceResolver,
                graphQLFetcherLoader: GraphQLFetcherLoader): GraphQL {

        val schemaParser = SchemaParser()
        val schemaGenerator = SchemaGenerator()

        // Parse the schema.
        val typeRegistry = TypeDefinitionRegistry();
        typeRegistry.merge(schemaParser.parse(
            BufferedReader(
                InputStreamReader(
                resourceResolver.getResourceAsStream("classpath:schema.graphqls").get())
            )
        ))

        validateFetchers(typeRegistry, graphQLFetcherLoader)

        // Create the runtime wiring.
        val runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query"){
                    it.dataFetchers(graphQLFetcherLoader.queryDict)
                }
                .type("Mutation") {
                    it.dataFetchers(graphQLFetcherLoader.mutationDict)
                }
                .build()

        // Create the executable schema.
        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)

        // Return the GraphQL bean.
        return GraphQL.newGraphQL(graphQLSchema).build()
    }

    private fun validateFetchers(typeRegistry: TypeDefinitionRegistry, graphQLFetcherLoader: GraphQLFetcherLoader){
        // Get the query and mutation types from the schema.
        val queryType = typeRegistry.getType("Query").get()
        val mutationType = typeRegistry.getType("Mutation").get()

        // Get the fields in the query and mutation types.
        val queryFields = queryType.children.filterIsInstance<FieldDefinition>()
        val mutationFields = mutationType.children.filterIsInstance<FieldDefinition>()

        // Check for missing fetchers.
        queryFields.forEach { field ->
            if (!graphQLFetcherLoader.queryDict.containsKey(field.name)) {
                logger.warn("Missing query fetcher for field ${field.name}")
            }
        }

        mutationFields.forEach { field ->
            if (!graphQLFetcherLoader.mutationDict.containsKey(field.name)) {
                logger.warn("Missing mutation fetcher for field ${field.name}")
            }
        }

    }
}