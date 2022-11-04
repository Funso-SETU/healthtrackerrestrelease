package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create{
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
                path("{user-id}"){
                    get(HealthTrackerController::getUserByUserId)
                    delete(HealthTrackerController::deleteUser)
                    patch(HealthTrackerController::updateUser)
                    path("activities"){
                        get(HealthTrackerController::getActivitiesByUserId)
                    }
                }
                path("/email/{email}"){ 
                    get(HealthTrackerController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(HealthTrackerController::getAllActivities)
                post(HealthTrackerController::addActivity)
            }

            path("/api/items") {
                get(HealthTrackerController::getAllItems)
                post(HealthTrackerController::addItem)
                path("{id}"){
                    get(HealthTrackerController::getItemById)
                    delete(HealthTrackerController::deleteItem)
                    patch(HealthTrackerController::updateItem)

            }
        }
    }
}
}