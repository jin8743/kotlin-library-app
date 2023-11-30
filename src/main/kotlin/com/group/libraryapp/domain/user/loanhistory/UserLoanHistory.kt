package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus.*
import javax.persistence.*
import javax.persistence.GenerationType.*

@Entity
class UserLoanHistory(

    @ManyToOne
    val user: User,

    val bookName: String,

    var status: UserLoanStatus = LOANED,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null
) {

    val isReturn: Boolean
        get() = this.status == RETURNED

    fun doReturn() {
        this.status = RETURNED
    }

    companion object {
        fun fixture(
            user: User,
            bookName: String,
            status: UserLoanStatus = LOANED,
            id: Long? = null
        ): UserLoanHistory {

            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id
            )
        }
    }
}