package com.m1guelgtz.devsync.Demo.Core.Routes

import kotlinx.serialization.Serializable

@Serializable
object RutaLogin

@Serializable
object RutaProjectList

@Serializable
data class RutaProjectDetail(val projectId: String)