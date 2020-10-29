package com.bbutner.arbiter.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="The item you requested could not be found")
class NotFoundException: RuntimeException() {}