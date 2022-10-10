package ie.setu.domain.repository

import ie.setu.domain.User

class UserDAO {

    private val users = arrayListOf<User>(
        User(name = "Alice", email = "alice@wonderland.com", id = 0),
        User(name = "Bob", email = "bob@cat.ie", id = 1),
        User(name = "Mary", email = "mary@contrary.com", id = 2),
        User(name = "Carol", email = "carol@singer.com", id = 3)
    )
    fun findById(id: Int): User?{
        return users.find {it.id == id}
    }

    fun getAll() : ArrayList<User>{
        return users
    }
    fun save(user: User){
        users.add(user)
    }

    fun findByEmail(email: String) :User?{
        return users.find { it.email == email }
    }


    fun delete(id: Int) {
        val user = findById(id)
        users.remove(user)
    }

    fun update(id: Int, user: User){
        val foundUser = findById(id)
        foundUser?.email = user.email
        foundUser?.name = user.name
        foundUser?.id = user.id
    }

}