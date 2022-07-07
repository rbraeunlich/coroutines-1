package dev.code_n_roll.coroutines1

import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(private val repository: EntityRepository) {

    @GetMapping(path = ["/entity/{id}"])
    suspend fun getEntity(@PathVariable id: String) = repository.findById(ObjectId(id))

}