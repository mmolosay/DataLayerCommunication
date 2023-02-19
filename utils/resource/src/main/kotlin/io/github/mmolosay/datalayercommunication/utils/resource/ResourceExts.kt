package io.github.mmolosay.datalayercommunication.utils.resource

/*
 * Extension functions for Resource.
 */

fun <T> Resource.Companion.success(value: T): Resource<T> =
    Resource.Success(value)

inline fun <T> Resource<T>.getOrElse(onFailure: (Resource.Failure) -> T): T =
    when (this) {
        is Resource.Success -> this.value
        is Resource.Failure -> onFailure(this)
    }

fun <T> Resource<T>.getOrNull(): T? =
    when (this) {
        is Resource.Success -> this.value
        else -> null
    }

fun <T> Resource<T>.getOrThrow(): T =
    when (this) {
        is Resource.Success -> this.value
        else -> throw IllegalStateException("Resource is a " + this::class::simpleName)
    }

inline fun <T, R> Resource<T>.map(transform: (value: T) -> R): Resource<R> =
    when (this) {
        is Resource.Success -> transform(this.value).let { Resource.success(it) }
        is Resource.Failure -> this
    }

inline fun <T, R> Resource<T>.fold(
    crossinline onSuccess: (value: T) -> R,
    crossinline onFailure: (failure: Resource.Failure) -> R
): R {
    return when (this) {
        is Resource.Success -> onSuccess(this.value)
        is Resource.Failure -> onFailure(this)
    }
}