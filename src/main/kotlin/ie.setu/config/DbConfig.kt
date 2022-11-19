package ie.setu.config

import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.name

class DbConfig{
    private val logger = KotlinLogging.logger {}
    //NOTE: you need the ?sslmode=require otherwise you get an error complaining about the ssl certificate
    fun getDbConnection() :Database{

        val logger = KotlinLogging.logger {}
        logger.info{"Starting DB Connection..."}

        val PGUSER = "vjanhzma"
        val PGPASSWORD = "cdiza1ZS6xVgIlm2Kf4htKAuAu2jhpun"
        val PGHOST = "lucky.db.elephantsql.com"
        val PGPORT = "5432"
        val PGDATABASE = "vjanhzma"

        //url format should be jdbc:postgresql://host:port/database
        val url = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        val dbConfig = Database.connect(url,
            driver="org.postgresql.Driver",
            user = PGUSER,
            password = PGPASSWORD
        )

        logger.info{"db url - connection: " + dbConfig.url}

        return dbConfig
    }

}