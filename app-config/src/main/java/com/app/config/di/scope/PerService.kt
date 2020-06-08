package com.app.config.di.scope

import kotlin.annotation.Retention
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerService