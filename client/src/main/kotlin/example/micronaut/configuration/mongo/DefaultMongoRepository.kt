package example.micronaut.configuration.mongo

import io.micronaut.data.mongodb.annotation.MongoRepository

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MongoRepository(databaseName="\${mongodb.database}")
annotation class DefaultMongoRepository()
