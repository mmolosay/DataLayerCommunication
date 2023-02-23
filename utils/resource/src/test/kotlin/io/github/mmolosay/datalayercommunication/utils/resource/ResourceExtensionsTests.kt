package io.github.mmolosay.datalayercommunication.utils.resource

import io.github.mmolosay.datalayercommunication.utils.resource.fakes.TestFailure
import io.kotest.assertions.fail
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import io.kotest.matchers.types.beOfType
import org.junit.Test

class ResourceExtensionsTests {

    @Test
    fun `Resource_Companion_success() creates Resource_Success`() {
        val value = "my value"

        val resource = Resource.success(value)

        resource should beOfType<Resource.Success<*>>()
    }

    @Test
    fun `Resource_isSuccess returns true, if Resource is Success`() {
        val resource = Resource.success("my value")

        val isSuccess = resource.isSuccess

        isSuccess shouldBe true
    }

    @Test
    fun `Resource_isSuccess returns false, if Resource is not Success`() {
        val resource: Resource<*> = TestFailure

        val isSuccess = resource.isSuccess

        isSuccess shouldBe false
    }

    @Test
    fun `Resource_isFailure returns true, if Resource is Failure`() {
        val resource: Resource<*> = TestFailure

        val isFailure = resource.isFailure

        isFailure shouldBe true
    }

    @Test
    fun `Resource_isFailure returns false, if Resource is not Failure`() {
        val resource = Resource.success("my value")

        val isFailure = resource.isFailure

        isFailure shouldBe false
    }

    @Test
    fun `Resource_getOrElse() returns initial value, if Resource is Success`() {
        val initialValue = "my value"
        val resource = Resource.success(initialValue)

        val obtainedValue = resource.getOrElse { fail("should not be executed") }

        obtainedValue shouldBe initialValue
    }

    @Test
    fun `Resource_getOrElse() returns onFailure value, if Resource is Failure`() {
        val failureValue = "fallback value"
        val resource: Resource<*> = TestFailure

        val obtainedValue = resource.getOrElse { failureValue }

        obtainedValue shouldBe failureValue
    }

    @Test
    fun `Resource_getOrNull() returns initial value, if Resource is Success`() {
        val initialValue = "my value"
        val resource = Resource.success(initialValue)

        val obtainedValue = resource.getOrNull()

        obtainedValue shouldBe initialValue
    }

    @Test
    fun `Resource_getOrNull() returns null, if Resource is Failure`() {
        val resource: Resource<*> = TestFailure

        val obtainedValue = resource.getOrNull()
        Result.success("").getOrNull()

        obtainedValue should beNull()
    }

    @Test
    fun `Resource_getOrThrow() doesn't throw exception, if Resource is Success`() {
        val resource = Resource.success("my value")

        shouldNotThrowAny { resource.getOrThrow() }
    }

    @Test
    fun `Resource_getOrThrow() returns initial value, if Resource is Success`() {
        val initialValue = "my value"
        val resource = Resource.success(initialValue)

        val obtainedValue = resource.getOrThrow()

        obtainedValue shouldBe initialValue
    }

    @Test
    fun `Resource_getOrThrow() throws exception, if Resource is Failure`() {
        val resource: Resource<*> = TestFailure

        shouldThrowAny { resource.getOrThrow() }
    }

    @Test
    fun `Resource_map() returns Success, if Resource is Success`() {
        val resource = Resource.success("my value")

        resource.map { 5 } // any transformation

        resource should beOfType<Resource.Success<*>>()
    }

    @Test
    fun `Resource_map() returns transoformed value, if Resource is Success`() {
        val resource = Resource.success("my value")
        val transformedValue = 5

        val transformed = resource.map { transformedValue }

        transformed.getOrThrow() shouldBe transformedValue
    }

    @Test
    fun `Resource_map() returns receiver Resource, if Resource is Failure`() {
        val resource: Resource<*> = TestFailure

        val transformed = resource.map { 5 } // any transformation

        transformed should beInstanceOf<Resource.Failure>()
    }

    @Test
    fun `Resource_fold() returns value of onSuccess(), if Resource is Success`() {
        val value = "my value"
        val resource = Resource.success(value)
        fun <T> onSuccess(value: T): Int = 5 // any transformation

        val folded = resource.fold(
            onSuccess = ::onSuccess,
            onFailure = { fail("should not be executed") },
        )

        val resultOnSuccess = onSuccess(value)
        folded shouldBe resultOnSuccess
    }

    @Test
    fun `Resource_fold() returns value of onFailure(), if Resource is Failure`() {
        val failure = TestFailure
        val resource: Resource<*> = failure
        fun onFailure(failure: Resource.Failure): Int = 5 // any transformation

        val folded = resource.fold(
            onSuccess = { fail("should not be executed") },
            onFailure = ::onFailure,
        )

        val resultOnFailure = onFailure(failure)
        folded shouldBe resultOnFailure
    }
}