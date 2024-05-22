package com.example.budgetperso.db

import java.time.LocalDateTime

class Account(
    val id: Int, // auto incrémenté
    val name: String,
    val icone: String,
    val balance: Double,
    val date: LocalDateTime,
    val user_Id: Int // Suppose que userId est l'identifiant de l'utilisateur associé à ce compte
)
