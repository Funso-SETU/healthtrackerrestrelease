package ie.setu.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.domain.Item
import ie.setu.helpers.*
import kotlin.test.assertEquals

//retrieving some test data from Fixtures
private val item1 = item.get(0)
private val item2 = item.get(1)
private val item3 = item.get(2)

class ItemDAOTest {

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateItems {

        @Test
        fun `multiple items added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three items
                val userDAO = populateUserTable()
                val itemDAO = populateItemTable()
                //Act & Assert
                assertEquals(3, itemDAO.getAll().size)
                assertEquals(item, itemDAO.getAllItems(item))

            }
        }
    }

    @Nested
    inner class ReadItems {

        @Test
        fun `getting all items from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val itemDAO = populateItemTable()
                //Act & Assert
                assertEquals(3, itemDAO.getAll().size)
            }
        }

    }

    @Nested
    inner class UpdateItems {

        @Test
        fun `updating existing item in table results in successful update`() {
            transaction {

                val itemDAO = populateItemTable()

                //Act & Assert
                val item1updated = Item(id = 1, name = "Mango", unitprice = 322.0, quantity = 230, dateadded = DateTime.now(), userid = 1)
                itemDAO.update(item1updated.id, item1updated)
                assertEquals(item1updated, itemDAO.findById(1))
            }
        }
    }

    @Nested
    inner class DeleteItems {


        @Test
        fun `deleting an existing activity (by id) in table results in record being deleted`() {
            transaction {
                val itemDAO = populateItemTable()

                //Act & Assert
                assertEquals(3, itemDAO.getAll().size)
                itemDAO.delete(item2.id)
                assertEquals(2, itemDAO.getAll().size)
            }
        }



    }
}