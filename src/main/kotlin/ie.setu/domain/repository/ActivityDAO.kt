package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.utils.mapToActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ActivityDAO {

    //Get all the activities in the database regardless of user id
    fun getAll(): ArrayList<Activity> {
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().map {
                activitiesList.add(mapToActivity(it)) }
        }
        return activitiesList
    }

    //Find a specific activity by activity id
    fun findByActivityId(id: Int): Activity?{
        return transaction {
            Activities
                .select() { Activities.id eq id}
                .map{mapToActivity(it)}
                .firstOrNull()
        }
    }

    //Find all activities for a specific user id
    fun findByUserId(userId: Int): List<Activity>{
        return transaction {
            Activities
                .select {Activities.userId eq userId}
                .map {mapToActivity(it)}
        }
    }

    //Save an activity to the database
    fun save(activity: Activity){
        transaction {
            Activities.insert {
                it[description] = activity.description
                it[duration] = activity.duration
                it[started] = activity.started
                it[calories] = activity.calories
                it[userId] = activity.userId
            }
        }
    }

    fun updateByActivityId(activityId: Int, activityDTO: Activity){
        transaction {
            Activities.update ({
                Activities.id eq activityId}) {
                it[description] = activityDTO.description
                it[duration] = activityDTO.duration
                it[started] = activityDTO.started
                it[calories] = activityDTO.calories
                it[userId] = activityDTO.userId
            }
        }
    }
    fun deleteByActivityId (activityId: Int): Int{
        return transaction{
            Activities.deleteWhere { Activities.id eq activityId }
        }
    }

    fun deleteByUserId (userId: Int): Int{
        return transaction{
            Activities.deleteWhere { Activities.userId eq userId }
        }
    }
}