package com.bbutner.arbiter.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["com.bbutner"])
@EnableR2dbcRepositories("com.bbutner.arbiter.persistence.dao")
class ArbiterApplication

fun main(args: Array<String>) {
    runApplication<ArbiterApplication>(*args)
}