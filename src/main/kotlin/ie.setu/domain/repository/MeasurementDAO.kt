package ie.setu.domain.repository

import ie.setu.domain.Measurement
import ie.setu.domain.db.Measurements
import ie.setu.utils.mapToMeasurement
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class MeasurementDAO {

    //Get all the body measurements in the database regardless of measurement id
    fun getAll(): ArrayList<Measurement> {
        val measurementsList: ArrayList<Measurement> = arrayListOf()
        transaction {
            Measurements.selectAll().map{
                measurementsList.add(mapToMeasurement(it)) }
        }
        return measurementsList
    }


    fun findById(id: Int): Measurement?{
        return transaction {
            Measurements.select {
                Measurements.id eq id}
                .map{ mapToMeasurement(it) }
                .firstOrNull()
        }
    }


    //Save a measurement to the database
    fun save(measurement: Measurement){
        transaction {
            Measurements.insert {
                it[chest] = measurement.chest
                it[waist] = measurement.waist
                it[dateadded] =measurement.dateadded
                it[userid] = measurement.userid
            }get Measurements.id
        }
    }


    fun delete(id: Int) {
        return transaction{
            Measurements.deleteWhere{
                Measurements.id eq id
            }
        }
    }

    fun update(id: Int, measurement: Measurement): Int{
        return transaction {
            Measurements.update ({
                Measurements.id eq id}) {
                it[chest] = measurement.chest
                it[waist] = measurement.waist
                it[dateadded] = measurement.dateadded
                it[userid] = measurement.userid
            }
        }
    }

    fun getAllMeasurements(measurements: ArrayList<Measurement>): ArrayList<Measurement> {
        return measurements
    }
}