package com.gitcoffee.jek.presentation.common

import com.android.volley.*
import com.gitcoffee.jek.data.Constants
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

open class AppUtilityTest {

    @get:Rule val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock lateinit var timeOutError:TimeoutError

    @Mock lateinit var serverError: ServerError

    @Mock lateinit var parseError: ParseError

    @Mock lateinit var noConnectionError: NoConnectionError

    @Mock lateinit var authFailureError:AuthFailureError

    @Before
    fun setUp() {
    }

    @Test
    fun getNetworkError() {
        val strTimeOutErrorExpected = Constants.VOLLEY_TIME_OUT_ERROR
        val strTimeOutErrorActual = AppUtility.getNetworkError(timeOutError).msg

        assertEquals(strTimeOutErrorExpected,strTimeOutErrorActual)


        val strNoConnectionErrorExpected = Constants.VOLLEY_NO_CONNECTION_ERROR
        val strNoConnectionErrorActual = AppUtility.getNetworkError(noConnectionError).msg

        assertEquals(strNoConnectionErrorExpected,strNoConnectionErrorActual)

        val strParseErrorExpected = Constants.VOLLEY_PARSING_ERROR
        val strParseErrorActual = AppUtility.getNetworkError(parseError).msg

        assertEquals(strParseErrorExpected,strParseErrorActual)

        val strAuthFailureErrorExpected = Constants.VOLLEY_SERVER_ERROR
        val strAuthFailureErrorActual = AppUtility.getNetworkError(serverError).msg

        assert(strAuthFailureErrorActual.contains(strAuthFailureErrorExpected))


        val strServerErrorExpected = Constants.VOLLEY_AUTH_ERROR
        val strServerErrorActual = AppUtility.getNetworkError(authFailureError).msg

        assert(strServerErrorActual.contains(strServerErrorExpected))
    }
}