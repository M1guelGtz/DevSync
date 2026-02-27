package com.m1guelgtz.devsync.Demo.Core.rutes

import kotlinx.serialization.Serializable

@Serializable
object RutaLogin

@Serializable
object RutaProjectList

@Serializable
data class RutaProjectDetail(val projectId: String)
