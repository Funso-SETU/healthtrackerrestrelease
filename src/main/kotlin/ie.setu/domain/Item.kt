 package ie.setu.domain

import org.joda.time.DateTime

data class Item (var id: Int,
                     var name: String,
					 var quantity: Int,
                     var unitprice: Double,
                     var dateadded: DateTime,
                     var userid: Int)