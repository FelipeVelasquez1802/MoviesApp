package org.`is`.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform