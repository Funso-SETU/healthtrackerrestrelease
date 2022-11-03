package ie.setu.domain.repository

import ie.setu.domain.Item
import ie.setu.domain.User
import ie.setu.domain.db.Items
import ie.setu.domain.db.Users
import ie.setu.utils.mapToItem
import ie.setu.utils.mapToUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ItemDAO {

    //Get all the items in the database regardless of item id
    fun getAll(): ArrayList<Item> {
        val itemsList: ArrayList<Item> = arrayListOf()
        transaction {
            Items.selectAll().map {
                itemsList.add(mapToItem(it)) }
        }
        return itemsList
    }

    //Find a specific item by item id
    fun findByItemId(id: Int): Item?{
        return transaction {
            Items
                .select() { Items.id eq id}
                .map{mapToItem(it)}
                .firstOrNull()
        }
    }

    fun findById(id: Int): Item?{
        return transaction {
            Items.select() {
                Items.id eq id}
                .map{ mapToItem(it) }
                .firstOrNull()
        }
    }

    //Find all items for a specific user id
    fun findByUserId(userId: Int): List<Item>{
        return transaction {
            Items
                .select {Items.userId eq userId}
                .map {mapToItem(it)}
        }
    }

    //Save an item to the database
    fun save(item: Item){
        transaction {
            Items.insert {
                it[name] = item.name
                it[quantity] = item.quantity
                it[unitPrice] = item.unitPrice
                it[dateAdded] = item.dateAdded
                it[userId] = item.userId
            }
        }
    }

    /**fun updateByItemId(itemId: Int, itemDTO: Item){
        transaction {
            Items.update ({
                Items.id eq itemId}) {
                 it[name] = item.name
                it[quantity] = item.quantity
                it[unitPrice] = item.unitPrice
                it[dateAdded] = item.dateAdded
                it[userId] = item.userId
            }
        }
    }**/
    fun deleteByItemId (itemId: Int): Int{
        return transaction{
            Items.deleteWhere { Items.id eq itemId }
        }
    }

    fun deleteByUserId (userId: Int): Int{
        return transaction{
            Items.deleteWhere { Items.userId eq userId }
        }
    }
}