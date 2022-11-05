package ie.setu.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kong.unirest.HttpResponse
import kong.unirest.JsonNode

//More info: https://www.baeldung.com/jackson-object-mapper-tutorial
//           https://www.baeldung.com/jackson-serialize-dates
//           https://www.baeldung.com/kotlin/reified-functions
//           https://www.baeldung.com/kotlin/generics

inline fun <reified T: Any> jsonToObject(json: String) : T
        = jacksonObjectMapper()
    .registerModule(JodaModule())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .readValue<T>(json)

inline fun <reified T: Any>  jsonNodeToObject(jsonNode : HttpResponse<JsonNode>) : T {
    return jsonToObject<T>(jsonNode.body.toString())
}

fun jsonObjectMapper(): ObjectMapper
        = ObjectMapper()
    .registerModule(JavaTimeModule())
    .registerModule(JodaModule())
    .registerModule(KotlinModule.Builder().build())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)