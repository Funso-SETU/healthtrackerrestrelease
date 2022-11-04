package ie.setu.config

import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.name

class DbConfig{
    private val logger = KotlinLogging.logger {}
    //NOTE: you need the ?sslmode=require otherwise you get an error complaining about the ssl certificate
    fun getDbConnection() :Database{

        logger.info{"Starting DB Connection..."}

        val dbConfig = Database.connect(
            "jdbc:postgresql://ec2-54-147-33-38.compute-1.amazonaws.com:5432/dd1alnffpluckj?sslmode=require",
            driver = "org.postgresql.Driver",
            user = "egbmysdsnonqzq",
            password = "d999c00dbc411a0061006a8b83997d197e8441ff4500b0db3ad84e203edd832e")
        logger.info{"DbConfig name = " + dbConfig.name}
        logger.info{"DbConfig url = " + dbConfig.url}

        return dbConfig
    }

}