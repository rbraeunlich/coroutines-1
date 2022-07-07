package dev.code_n_roll.coroutines1

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id


data class TestEntity(@Id val id: ObjectId)
