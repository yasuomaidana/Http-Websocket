package example.micronaut.exception

class NullIdException(
    type:String? = null,
    message:String? = if (type != null) "$type id is required" else null
): Exception(message)