package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ie.setu.domain.Activity
import ie.setu.domain.Item
import ie.setu.domain.User
import ie.setu.domain.Measurement
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.ItemDAO
import ie.setu.domain.repository.MeasurementDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

object HealthTrackerController {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()
    private val itemDao = ItemDAO()
    private val measurementDao = MeasurementDAO()

    @OpenApi(
        summary = "Get all users",
        operationId = "getAllUsers",
        tags = ["User"],
        path = "/api/users",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<User>::class)])]
    )
    fun getAllUsers(ctx: Context) {
        val users = userDao.getAll()
        if (users.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(users)
    }

    @OpenApi(
        summary = "Get all items",
        operationId = "getAllItems",
        tags = ["Item"],
        path = "/api/items",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<Item>::class)])]
    )
    fun getAllItems(ctx: Context) {
        val items = itemDao.getAll()
        if (items.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(items)
    }
    @OpenApi(
        summary = "Get all measurements",
        operationId = "getAllMeasurements",
        tags = ["Measurement"],
        path = "/api/measurements",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<Measurement>::class)])]
    )
    fun getAllMeasurements(ctx: Context) {
        val measurements = measurementDao.getAll()
        if (measurements.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(measurements)
    }

    @OpenApi(
        summary = "Get user by ID",
        operationId = "getUserById",
        tags = ["User"],
        path = "/api/users/{user-id}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("user-id", Int::class, "The user ID")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )
    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }


    @OpenApi(
        summary = "Get item by ID",
        operationId = "getItemById",
        tags = ["Item"],
        path = "/api/items/{id}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("id", Int::class, "The item ID")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )
    fun getItemById(ctx: Context) {
        val item = itemDao.findById(ctx.pathParam("id").toInt())
        if (item != null) {
           ctx.json(item)
        }
    }

    @OpenApi(
        summary = "Get measurement by ID",
        operationId = "getMeasurementById",
        tags = ["Measurement"],
        path = "/api/measurements/{id}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("id", Int::class, "The measurement ID")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )
    fun getMeasurementById(ctx: Context) {
        val measurement = measurementDao.findById(ctx.pathParam("id").toInt())
        if (measurement != null) {
            ctx.json(measurement)
        }
    }

    @OpenApi(
        summary = "Add User",
        operationId = "addUser",
        tags = ["User"],
        path = "/api/users",
        method = HttpMethod.POST,
        pathParams = [OpenApiParam("user-id", Int::class, "The user ID")],
        responses  = [OpenApiResponse("200")]
    )
    fun addUser(ctx: Context) {
        val user : User = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        if (userId != null) {
            user.id = userId
            ctx.json(user)
            ctx.status(201)
        }
    }
    @OpenApi(
        summary = "Add Item",
        operationId = "addItem",
        tags = ["Item"],
        path = "/api/items",
        method = HttpMethod.POST,
        pathParams = [OpenApiParam("id", Int::class, "The item ID")],
        responses  = [OpenApiResponse("200")]
    )
    fun addItem(ctx: Context) {
        val item: Item = jsonToObject(ctx.body())
        itemDao.save(item)
        ctx.json(item)
    }

    @OpenApi(
        summary = "Add Measurement",
        operationId = "addMeasurement",
        tags = ["Measurement"],
        path = "/api/measurements",
        method = HttpMethod.POST,
        pathParams = [OpenApiParam("id", Int::class, "The measurement ID")],
        responses  = [OpenApiResponse("200")]
    )
    fun addMeasurement(ctx: Context) {
        val measurement: Measurement = jsonToObject(ctx.body())
        measurementDao.save(measurement)
        ctx.json(measurement)
    }

    @OpenApi(
        summary = "Get user by Email",
        operationId = "getUserByEmail",
        tags = ["User"],
        path = "/api/users/email/{email}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("email", Int::class, "The user email")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )
    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    @OpenApi(
        summary = "Delete user by ID",
        operationId = "deleteUserById",
        tags = ["User"],
        path = "/api/users/{user-id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-id", Int::class, "The user ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun deleteUser(ctx: Context) {
        if (userDao.delete(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

        @OpenApi(
            summary = "Delete item by ID",
            operationId = "deleteItemById",
            tags = ["User"],
            path = "/api/items/{id}",
            method = HttpMethod.DELETE,
            pathParams = [OpenApiParam("id", Int::class, "The item ID")],
            responses = [OpenApiResponse("204")]
        )
        fun deleteItem(ctx: Context) {
            itemDao.delete(ctx.pathParam("id").toInt())
        }

    @OpenApi(
        summary = "Delete measurement by ID",
        operationId = "deleteMeasurementById",
        tags = ["Measurement"],
        path = "/api/measurements/{id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("id", Int::class, "The measurement ID")],
        responses = [OpenApiResponse("204")]
    )
    fun deleteMeasurement(ctx: Context) {
        measurementDao.delete(ctx.pathParam("id").toInt())
    }

        @OpenApi(
            summary = "Update user by ID",
            operationId = "updateUserById",
            tags = ["User"],
            path = "/api/users/{user-id}",
            method = HttpMethod.PATCH,
            pathParams = [OpenApiParam("user-id", Int::class, "The user ID")],
            responses = [OpenApiResponse("204")]
        )
        fun updateUser(ctx: Context) {
            val foundUser: User = jsonToObject(ctx.body())
            if ((userDao.update(id = ctx.pathParam("user-id").toInt(), user = foundUser)) != 0)
                ctx.status(204)
            else
                ctx.status(404)
        }

        @OpenApi(
            summary = "Update item by ID",
            operationId = "updateItemById",
            tags = ["Item"],
            path = "/api/items/{id}",
            method = HttpMethod.PATCH,
            pathParams = [OpenApiParam("id", Int::class, "The item ID")],
            responses = [OpenApiResponse("204")]
        )
        fun updateItem(ctx: Context) {
            val item: Item = jsonToObject(ctx.body())
            itemDao.update(
                id = ctx.pathParam("id").toInt(),
                item = item
            )
        }
    @OpenApi(
        summary = "Update measurement by ID",
        operationId = "updateMeasurementById",
        tags = ["Measurement"],
        path = "/api/measurements/{id}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("id", Int::class, "The measurement ID")],
        responses = [OpenApiResponse("204")]
    )
    fun updateMeasurement(ctx: Context) {
        val measurement: Measurement = jsonToObject(ctx.body())
        measurementDao.update(
            id = ctx.pathParam("id").toInt(),
            measurement = measurement
        )
    }

        //--------------------------------------------------------------
        // ActivityDAOI specifics
        //-------------------------------------------------------------

        fun getAllActivities(ctx: Context) {
            //mapper handles the deserialization of Joda date into a String.
            val mapper = jacksonObjectMapper()
                .registerModule(JodaModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            ctx.json(mapper.writeValueAsString(activityDAO.getAll()))
        }

        fun getActivitiesByUserId(ctx: Context) {
            if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
                val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
                if (activities.isNotEmpty()) {
                    //mapper handles the deserialization of Joda date into a String.
                    val mapper = jacksonObjectMapper()
                        .registerModule(JodaModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    ctx.json(mapper.writeValueAsString(activities))
                }
            }
        }

        fun addActivity(ctx: Context) {
            //mapper handles the serialisation of Joda date into a String.
            val activity: Activity = jsonToObject(ctx.body())
            activityDAO.save(activity)
            ctx.json(activity)
        }

        fun getActivitiesByActivityId(ctx: Context) {
            val activity = activityDAO.findByActivityId((ctx.pathParam("activity-id").toInt()))
            if (activity != null) {
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activity))
            }
        }

        fun deleteActivityByActivityId(ctx: Context) {
            activityDAO.deleteByActivityId(ctx.pathParam("activity-id").toInt())
        }

        fun deleteActivityByUserId(ctx: Context) {
            activityDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
        }

        fun updateActivity(ctx: Context) {
            val activity: Activity = jsonToObject(ctx.body())
            activityDAO.updateByActivityId(
                activityId = ctx.pathParam("activity-id").toInt(),
                activityDTO = activity
            )
        }
    }