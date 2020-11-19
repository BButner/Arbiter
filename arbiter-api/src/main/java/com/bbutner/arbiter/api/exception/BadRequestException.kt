package com.bbutner.arbiter.api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The request was not valid due to invalid syntax or data")
class BadRequestException: RuntimeException() {}