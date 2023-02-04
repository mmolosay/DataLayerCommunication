import io.github.mmolosay.datalayercommunication.data.handheld.AnimalsRepositoryImpl
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import io.kotest.matchers.shouldNot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimalsRepositoryImplTests {

    private val animalId = 2L

    @Test
    fun `deleteAnimalById returns null, when animals list is empty`() = runTest {
        val repository = AnimalsRepositoryImpl(emptyList())

        val deletedAnimal = repository.deleteAnimalById(animalId)

        deletedAnimal should beNull()
    }

    @Test
    fun `deleteAnimalById returns some animal`() = runTest {
        val repository = AnimalsRepositoryImpl(listOfOneAnimal())

        val deletedAnimal = repository.deleteAnimalById(animalId)

        deletedAnimal shouldNot beNull()
    }

    @Test
    fun `deleteAnimalById deletes some animal`() = runTest {
        val repository = AnimalsRepositoryImpl(listOfOneAnimal())
        val getAnimalsCount = suspend { repository.getAllAnimals().size }
        val animalsCountBefore = getAnimalsCount()

        repository.deleteAnimalById(animalId)

        val newAnimalsCount = getAnimalsCount()
        newAnimalsCount shouldBe (animalsCountBefore - 1)
    }

    @Test
    fun `deleteAnimalById deletes animal with specified id`() = runTest {
        val repository = AnimalsRepositoryImpl(listOfOneAnimal())

        val deletedAnimal = repository.deleteAnimalById(animalId)

        deletedAnimal!!.id shouldBe animalId
    }

    @Test
    fun `getAllAnimals returns passed animals`() = runTest {
        val initialAnimals = listOfOneAnimal()
        val repository = AnimalsRepositoryImpl(initialAnimals)

        val obtainedAnimals = repository.getAllAnimals()

        obtainedAnimals shouldBe initialAnimals
    }

    @Test
    fun `getAllAnimals returns up to date animals`() = runTest {
        val initialAnimals = listOfOneAnimal()
        val repository = AnimalsRepositoryImpl(initialAnimals)

        val deletedAnimal = repository.deleteAnimalById(animalId)
        val obtainedAnimals = repository.getAllAnimals()

        val expectedAnimals = initialAnimals.toMutableList().apply { remove(deletedAnimal) }
        obtainedAnimals shouldBe expectedAnimals
    }

    private fun listOfOneAnimal(): List<Animal> =
        listOf(
            Animal(
                id = animalId,
                species = Animal.Species.Dog,
                name = "Bark",
                age = 1,
            ),
        )
}