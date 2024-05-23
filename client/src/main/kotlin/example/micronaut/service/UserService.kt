package example.micronaut.service

import example.micronaut.entities.Book
import example.micronaut.entities.UserBookId
import example.micronaut.repository.BookRepository
import example.micronaut.repository.UserBookRepository
import example.micronaut.repository.UserRepository
import jakarta.inject.Singleton

@Singleton
class UserService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val userBookRepository: UserBookRepository
) {
    fun getBooksForUser(userId: Long):List<Book>{
        val userBooks = userBookRepository.findByUserId(userId)
        val bookIds = userBooks.map { bookRepository.findById(it.id.bookId).orElse(null) }
        return bookIds.filterNotNull()
    }
    fun deleteBookForUser(userId: Long, bookId: Long){
        val userBook = userBookRepository.findById(UserBookId(userId, bookId)).orElse(null)
        if(userBook != null){
            userBookRepository.delete(userBook)
        }
    }
}