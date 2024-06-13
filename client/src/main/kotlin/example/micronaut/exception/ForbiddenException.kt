package example.micronaut.exception

class ForbiddenException(
    user:String = "",
    action: String?,
    message: String = "User $user not authorized to $action"
    ) : Exception(message)