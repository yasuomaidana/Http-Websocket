package example.micronaut.exception

class NotFoundException(
    type: String = "Resource",
    id: String = "",
    message: String = "$type with id $id not found"
) :Exception(message)