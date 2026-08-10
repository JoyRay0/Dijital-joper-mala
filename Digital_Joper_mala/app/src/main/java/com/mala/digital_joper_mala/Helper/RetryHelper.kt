package com.mala.digital_joper_mala.Helper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class RetryHelper(
    private val scope : CoroutineScope
) {

    fun retry(
        maxTime : Long = 60_000,
        delayTime : Long = 5_000,
        request : (success : () -> Unit, failed : () -> Unit) -> Unit,
        onSuccess : () -> Unit,
        onFailed : () -> Unit

    ){

        val startTime = System.currentTimeMillis()

        fun execute(){

            if (System.currentTimeMillis() - startTime >= maxTime){

                onFailed()
                return

            }

            request( {

                onSuccess()

            }, {

                scope.launch {

                    delay(delayTime.milliseconds)

                    execute()

                }

            } )

        }

        execute()

    }

}