package com.gitcoffee.jek.network


data class Resource<ResultType>(var status: Status, var data: ResultType? = null, var error: NetworkCustomError? = null) {

    companion object {
        /**
         * Creates [Resource] with [Status.SUCCESS] and [data]
         */
        fun <ResultType> success(data: ResultType): Resource<ResultType> =
            Resource(
                Status.SUCCESS,
                data
            )

        /**
         * Creates [Resource] with [Status.LOADING] to notify UI to load
         */
        fun <ResultType> loading(): Resource<ResultType> =
            Resource(
                Status.LOADING
            )

        /**
         * Creates [Resource] with [Status.ERROR] and passes the error object
         */
        fun <ResultType> error(NetworkCustomError: NetworkCustomError?): Resource<ResultType> =
            Resource(
                Status.ERROR,
                error = NetworkCustomError
            )

    }
}