package com.mahmoud.altasherat.features.authentication.login.data.mappers

interface Mapper<DTO, Domain, Entity> {

    fun dtoToDomain(input: DTO): Domain
    fun domainToEntity(input: Domain): Entity

}