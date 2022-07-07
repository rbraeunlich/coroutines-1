package dev.code_n_roll.coroutines1

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EntityRepository: CoroutineCrudRepository<TestEntity, ObjectId>