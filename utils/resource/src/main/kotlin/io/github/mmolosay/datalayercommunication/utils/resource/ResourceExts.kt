package io.github.mmolosay.datalayercommunication.utils.resource

/*
 * Extension functions for Resource.
 */

/**
 * Creates [Resource.Success] with specified [value].
 */
fun <T> Resource.Companion.success(value: T): Resource<T> =
    Resource.Success(value)

fun Resource.Companion.success(): Resource<Unit> =
    Resource.Success(Unit)

/**
 * Determines, whether receiver [Resource] is an instance of [Resource.Success].
 */
val Resource<*>.isSuccess: Boolean
    get() = this is Resource.Success

/**
 * Determines, whether receiver [Resource] is an instance of [Resource.Failure].
 */
val Resource<*>.isFailure: Boolean
    get() = this is Resource.Failure

/**
 * Returns the encapsulated value, if receiver [Resource] is [Resource.Success],
 * or the result of [onFailure] function, if it is a [Resource.Failure].
 */
inline fun <T> Resource<T>.getOrElse(onFailure: (Resource.Failure) -> T): T =
    when (this) {
        is Resource.Success -> this.value
        is Resource.Failure -> onFailure(this)
    }

/**
 * Returns the encapsulated value, if receiver [Resource] is [Resource.Success],
 * or `null`, if it is anything else.
 */
fun <T> Resource<T>.getOrNull(): T? =
    when (this) {
        is Resource.Success -> this.value
        else -> null
    }

/**
 * Returns the encapsulated value, if receiver [Resource] is [Resource.Success],
 * or `throw`s an [IllegalStateException], if it is anything else.
 */
fun <T> Resource<T>.getOrThrow(): T =
    when (this) {
        is Resource.Success -> this.value
        else -> throw IllegalStateException("Resource is a " + this::class::simpleName)
    }

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated value,
 * if receiver [Resource] is [Resource.Success], or the original [Resource.Failure].
 */
inline fun <T, R> Resource<T>.map(transform: (value: T) -> R): Resource<R> =
    when (this) {
        is Resource.Success -> transform(this.value).let { Resource.success(it) }
        is Resource.Failure -> this
    }

/**
 * Returns the result of [onSuccess] for the encapsulated value,
 * if receiver [Resource] is [Resource.Success],
 * or the result of [onFailure] function, if it is a [Resource.Failure].
 */
inline fun <T, R> Resource<T>.fold(
    crossinline onSuccess: (value: T) -> R,
    crossinline onFailure: (failure: Resource.Failure) -> R
): R =
    when (this) {
        is Resource.Success -> onSuccess(this.value)
        is Resource.Failure -> onFailure(this)
    }