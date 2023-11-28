package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.GenerationType.*
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class UserLoanHistory(

    @ManyToOne
    val user: User,

    val bookName: String,

    var isReturn: Boolean,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null
) {

    fun doReturn() {
        this.isReturn = true
    }
}