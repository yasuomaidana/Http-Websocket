package example.micronaut.graphql

import example.micronaut.graphql.fetcher.postsIt.*
import example.micronaut.graphql.fetcher.postsIt.comments.CommentFetcher
import example.micronaut.graphql.fetcher.postsIt.comments.CreateCommentFetcher
import graphql.GraphQL
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

    @Bean
    @Singleton
    fun graphQL(resourceResolver: ResourceResolver,
                postItFetcher: PostItFetcher,
                createPostItFetcher: CreatePostItFetcher,
                postsFetcher: PostsFetcher,
                commentFetcher: CommentFetcher,
                createCommentFetcher: CreateCommentFetcher,
                addChildPostItFetcher: AddChildPostItFetcher,
                createChildPostItFetcher: CreateChildPostItFetcher,
                removeChildPostItFetcher: AddChildPostItFetcher,
                changeParentPostItFetcher: AddChildPostItFetcher
    ): GraphQL {

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

        // Create the runtime wiring.
        val runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query") { typeWiring -> typeWiring
                        .dataFetcher("postIt", postItFetcher)
                        .dataFetcher("posts", postsFetcher)
                    .dataFetcher("comment", commentFetcher)
                }
                .type("Mutation") { typeWiring -> typeWiring
                        .dataFetcher("createPostIt", createPostItFetcher)
                        .dataFetcher("createComment", createCommentFetcher)
                        .dataFetcher("addChildPostIt", addChildPostItFetcher)
                        .dataFetcher("createChildPostIt", createChildPostItFetcher)
                        .dataFetcher("removeChildPostIt", removeChildPostItFetcher)
                        .dataFetcher("changeParentPostIt", changeParentPostItFetcher)
                }
                .build()

        // Create the executable schema.
        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)

        // Return the GraphQL bean.
        return GraphQL.newGraphQL(graphQLSchema).build()
    }
}