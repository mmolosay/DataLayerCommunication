package com.example.domain.communication.model.response

import com.example.domain.Animal
import kotlinx.serialization.Serializable

@Serializable
class DeleteAnimalByIdResponse(
    val data: Animal?,
) : Response()