package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one item.
//       Database wise, this is the table object.

object Items : Table("items") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val unitPrice = double("unitPrice")
    val quantity = integer("quantity")
    val dateAdded = datetime("dateAdded")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}

