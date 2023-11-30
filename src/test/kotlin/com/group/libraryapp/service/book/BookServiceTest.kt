package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType.*
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus.*
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,

    private val bookRepository: BookRepository,

    private val userRepository: UserRepository,

    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {


    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 동작한다")
    fun saveBookTest() {
        //given
        val request = BookRequest("가나다", COMPUTER)

        //when
        bookService.saveBook(request)

        //then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("가나다")
        assertThat(books[0].type).isEqualTo(COMPUTER)
    }

    @Test
    @DisplayName("책 대출이 정상 동작한다")
    fun loanBook() {
        //given
        bookRepository.save(Book.fixture())
        val savedUser = userRepository.save(User("이영진", 10))
        val request = BookLoanRequest("이영진", "가나다")


        //when
        bookService.loanBook(request)

        //then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("가나다")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].status).isEqualTo(LOANED)
    }

    @Test
    @DisplayName("책이 이미 대출되어 있으면, 신규 대출이 실패한다")
    fun loanBookFailTest() {
        //given
        bookRepository.save(Book.fixture())
        val savedUser = userRepository.save(User("이영진", 10))
        bookService.loanBook(BookLoanRequest("이영진", "가나다"))

        //when then
        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(BookLoanRequest("이영진", "가나다"))
        }.message
        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책 반납이 정상 동작한다")
    fun returnBookTest() {
        //given
        bookRepository.save(Book.fixture())
        val savedUser = userRepository.save(User("이영진", 10))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "이상한 나라의 엘리스"))
        val request = BookReturnRequest("이영진", "이상한 나라의 엘리스")

        //when
        bookService.returnBook(request)

        //then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].status).isEqualTo(RETURNED)
    }


    @Test
    @DisplayName("책 대여 권수를 정상 확인한다")
    fun countLoanedBookTest() {
        //given
        val savedUser = userRepository.save(User("이영진", null))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(savedUser, "a"),
                UserLoanHistory.fixture(savedUser, "b", RETURNED),
                UserLoanHistory.fixture(savedUser, "c", RETURNED)
            )
        )

        //when
        val result = bookService.countLoanedBook()


        //then
        assertThat(result).isEqualTo(1)
    }

    @Test
    @DisplayName("분야별 책 권수를 정상 확인한다")
    fun getBookStatisticsTest() {
        //given
      bookRepository.saveAll(
          listOf(
              Book.fixture("A", COMPUTER),
              Book.fixture("B", COMPUTER),
              Book.fixture("C", SCIENCE)
          )
      )

        //when
        val results = bookService.getBookStatistics()

        //then
        assertThat(results).hasSize(2)
        val computerDto = results.first { result -> result.type == COMPUTER }
        assertThat(computerDto.count).isEqualTo(2)

        val scienceDto = results.first { result -> result.type == SCIENCE }
        assertThat(scienceDto.count).isEqualTo(1)
    }
}