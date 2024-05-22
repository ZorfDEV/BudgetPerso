package com.example.budgetperso.db

import java.time.LocalDateTime


class Transactions (
    val id: Int,
    val name: String,
    val description: String,
    val amount: Double,
    val date: LocalDateTime,
    val account_id: Int

)
