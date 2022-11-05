package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one item.
//       Database wise, this is the table object.

object Measurements : Table("measurements") {
    val id = integer("id").autoIncrement().primaryKey()
    val chest = double("chest")
    val waist = double("waist")
    val dateadded = datetime("dateadded")
    val userid = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}

