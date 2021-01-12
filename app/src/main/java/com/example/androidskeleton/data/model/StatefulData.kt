package com.example.androidskeleton.data.model

data class StatefulData<out T>(
    val state: STATE,
    val data: T?,
    val error: Error?
) {
    companion object {
        fun <T> success(data: T): StatefulData<T> {
           return StatefulData<T>(STATE.SUCCESS, data, null)
        }

        fun <T> loading(data: T? = null): StatefulData<T> {
            return StatefulData<T>(STATE.LOADING, null, null)
        }

        fun <T> error(error: Error? = null): StatefulData<T> {
            return StatefulData<T>(STATE.ERROR, null, error)
        }
    }
}

enum class STATE {
    LOADING,
    SUCCESS,
    ERROR
}