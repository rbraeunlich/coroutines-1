package dev.code_n_roll.coroutines1

import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
internal class TestControllerTest(
    @Autowired private val entityRepository: EntityRepository,
    @LocalServerPort private val localPort: Int
) {
    companion object {
        @JvmStatic
        @Container
        private val mongoDBContainer: MongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))

        @JvmStatic
        @DynamicPropertySource
        fun mongoProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
        }
    }

    @Test
    fun `should retrieve entity`() {
        val id = ObjectId()
        runBlocking { entityRepository.save(TestEntity(id)) }
        val webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:$localPort").build()
        webTestClient.get().uri("/entity/${id.toHexString()}")
            .exchange()
            .expectStatus()
            .isOk
    }

}