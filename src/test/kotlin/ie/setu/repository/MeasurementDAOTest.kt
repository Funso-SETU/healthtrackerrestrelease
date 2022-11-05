package ie.setu.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.domain.Item
import ie.setu.domain.Measurement
import ie.setu.helpers.*
import kotlin.test.assertEquals

//retrieving some test data from Fixtures
private val meast1 = measurements.get(0)
private val meast2 = measurements.get(1)
private val meast3 = measurements.get(2)

class MeasurementDAOTest {

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
        fun `multiple measurements added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three items
                val measurementDAO = populateMeasurementTable()
                //Act & Assert
                assertEquals(3, measurementDAO.getAll().size)
                assertEquals(measurements, measurementDAO.getAllMeasurements(measurements))

            }
        }
    }

    @Nested
    inner class ReadItems {

        @Test
        fun `getting all measurements from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val measurementDAO = populateMeasurementTable()
                //Act & Assert
                assertEquals(3, measurementDAO.getAll().size)
            }
        }

    }

    @Nested
    inner class UpdateItems {

        @Test
        fun `updating existing measurement in table results in successful update`() {
            transaction {
                val userDAO = populateUserTable()
                val measurementDAO = populateMeasurementTable()

                //Act & Assert
                val meast1updated = Measurement(id = 1, chest = 23.0, waist = 22.0, dateadded = DateTime.now(), userid = 1)
                measurementDAO.update(meast1updated.id, meast1updated)
                assertEquals(meast1updated, measurementDAO.findById(1))
            }
        }
    }

    @Nested
    inner class DeleteItems {


        @Test
        fun `deleting an existing measurement (by id) in table results in record being deleted`() {
            transaction {
                val measurementDAO = populateMeasurementTable()

                //Act & Assert
                assertEquals(3, measurementDAO.getAll().size)
                measurementDAO.delete(meast2.id)
                assertEquals(2, measurementDAO.getAll().size)
            }
        }



    }
}