 package ie.setu.domain

import org.joda.time.DateTime

data class Measurement (var id: Int,
                     var chest: Double,
					 var waist: Double,
                     var dateadded: DateTime,
                     var userid: Int)