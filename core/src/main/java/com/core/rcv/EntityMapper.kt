package com.core.rcv

interface EntityMapper<M : Model, ME : ModelEntity> {

    fun mapToDomain(entity: ME): M

    fun mapToEntity(model: M): ME
}