package ie.setu.config


import ie.setu.controllers.HealthTrackerController
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.ReDocOptions
import io.swagger.v3.oas.models.info.Info

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create{
            it.registerPlugin(getConfiguredOpenApiPlugin())
            it.defaultContentType = "application/json"
            //added this jsonMapper for our integration tests - serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
        }

        app.exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        app.error(404) { ctx -> ctx.json("404 - Not Found") }
        app.start(getHerokuAssignedPort())

        registerRoutes(app)
        return app
    }
    private fun getHerokuAssignedPort(): Int {
        val herokuPort = System.getenv("PORT")
        return if (herokuPort != null) {
            Integer.parseInt(herokuPort)
        } else 7000
    }

    private fun registerRoutes(app: Javalin) {
        app.routes {
            path("/api/users") {
                get(HealthTrackerController::getAllUsers)
                post(HealthTrackerController::addUser)
                path("{user-id}") {
                    get(HealthTrackerController::getUserByUserId)
                    delete(HealthTrackerController::deleteUser)
                    patch(HealthTrackerController::updateUser)
                    path("activities") {
                        get(HealthTrackerController::getActivitiesByUserId)
                        delete(HealthTrackerController::deleteActivityByUserId)
                    }
                }
                path("/email/{email}") {
                    get(HealthTrackerController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(HealthTrackerController::getAllActivities)
                post(HealthTrackerController::addActivity)
                path("{activity-id}") {
                    get(HealthTrackerController::getActivitiesByActivityId)
                    delete(HealthTrackerController::deleteActivityByActivityId)
                    patch(HealthTrackerController::updateActivity)
                }

            }
            path("/api/items") {
                get(HealthTrackerController::getAllItems)
                post(HealthTrackerController::addItem)
                path("{id}") {
                    get(HealthTrackerController::getItemById)
                    delete(HealthTrackerController::deleteItem)
                    patch(HealthTrackerController::updateItem)

                }
            }
            path("/api/measurements") {
                get(HealthTrackerController::getAllMeasurements)
                post(HealthTrackerController::addMeasurement)
                path("{id}") {
                    get(HealthTrackerController::getMeasurementById)
                    delete(HealthTrackerController::deleteMeasurement)
                    patch(HealthTrackerController::updateMeasurement)

                }
            }
        }
    }
    private fun getConfiguredOpenApiPlugin() = OpenApiPlugin(
        OpenApiOptions(
            Info().apply {
                title("Health Tracker App")
                version("1.0")
                description("Health Tracker API")
    }
        ).apply {
            path("/swagger-docs") // endpoint for OpenAPI json
            swagger(SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
            reDoc(ReDocOptions("/redoc")) // endpoint for redoc
        }
    )
}