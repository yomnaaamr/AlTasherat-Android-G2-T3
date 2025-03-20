package com.mahmoud.altasherat.features.login.data.mappers

interface Mapper<DTO, Domain, Entity> {

    fun dtoToEntity(input: DTO): Entity
    fun dtoToDomain(input: DTO): Domain
    fun domainToEntity(input: Domain): Entity

}