package ie.setu.helpers

import ie.setu.domain.Activity
import ie.setu.domain.Item
import ie.setu.domain.Measurement
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Items
import ie.setu.domain.db.Measurements
import ie.setu.domain.db.Users
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.ItemDAO
import ie.setu.domain.repository.MeasurementDAO
import ie.setu.domain.repository.UserDAO
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4)
)

val activities = arrayListOf<Activity>(
    Activity(id = 1, description = "Running", duration = 22.0, calories = 230, started = DateTime.now(), userId = 1),
    Activity(id = 2, description = "Hopping", duration = 10.5, calories = 80, started = DateTime.now(), userId = 1),
    Activity(id = 3, description = "Walking", duration = 12.0, calories = 120, started = DateTime.now(), userId = 2)
)

val item = arrayListOf<Item>(
    Item(id = 1, name = "Mango", unitprice = 22.0, quantity = 230, dateadded = DateTime.now(), userid = 1),
    Item(id = 2, name = "Strawberry", unitprice = 220.0, quantity = 30, dateadded = DateTime.now(), userid = 1),
    Item(id = 3, name = "Apple", unitprice = 2.0, quantity = 23, dateadded = DateTime.now(), userid = 1),
)

val measurements = arrayListOf<Measurement>(
    Measurement(id = 1, chest = 23.0, waist = 22.0, dateadded = DateTime.now(), userid = 1),
    Measurement(id = 1, chest = 23.0, waist = 22.0, dateadded = DateTime.now(), userid = 1),
    Measurement(id = 1, chest = 23.0, waist = 22.0, dateadded = DateTime.now(), userid = 1),
)

fun populateUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    userDAO.save(users.get(1))
    userDAO.save(users.get(2))
    return userDAO
}
fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities.get(0))
    activityDAO.save(activities.get(1))
    activityDAO.save(activities.get(2))
    return activityDAO
}

fun populateItemTable(): ItemDAO {
    SchemaUtils.create(Items)
    val itemDAO = ItemDAO()
    itemDAO.save(item.get(0))
    itemDAO.save(item.get(1))
    itemDAO.save(item .get(2))
    return itemDAO
}

fun populateMeasurementTable(): MeasurementDAO {
    SchemaUtils.create(Measurements)
    val measurementDAO = MeasurementDAO()
    measurementDAO.save(measurements.get(0))
    measurementDAO.save(measurements.get(1))
    measurementDAO.save(measurements.get(2))
    return measurementDAO
}