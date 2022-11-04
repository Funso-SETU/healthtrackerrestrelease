package ie.setu.utils

import ie.setu.domain.Activity
import ie.setu.domain.Item
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Items
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)

fun mapToItem(it: ResultRow) = Item(
    id = it[Items.id],
    name = it[Items.name],
    quantity = it[Items.quantity],
    dateadded = it[Items.dateadded],
    unitprice = it[Items.unitprice],
    userid = it[Items.userid]
)