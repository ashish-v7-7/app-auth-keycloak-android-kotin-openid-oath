package com.protium.protiumauthkeycloak.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.protium.protiumauthkeycloak.R


class MainActivity : AppCompatActivity() {
    private val username: EditText? = null
    private  var password:EditText? = null
    private val btnLogin: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
//
//    private fun exchangeAuthorizationCode(authorizationResponse: AuthorizationResponse) {
//        displayLoading("Exchanging authorization code")
//        performTokenRequest(
//            authorizationResponse.createTokenExchangeRequest(),
//            this::handleCodeExchangeResponse
//        )
//    }
//
//    private fun performTokenRequest(
//        request: TokenRequest,
//        callback: TokenResponseCallback
//    ) {
//        val clientAuthentication: ClientAuthentication
//        clientAuthentication = try {
//            mStateManager.getCurrent().getClientAuthentication()
//        } catch (ex: UnsupportedAuthenticationMethod) {
//            Log.d(
//                TAG, "Token request cannot be made, client authentication for the token "
//                        + "endpoint could not be constructed (%s)", ex
//            )
//            displayNotAuthorized("Client authentication method is unsupported")
//            return
//        }
//        mAuthService.performTokenRequest(
//            request,
//            clientAuthentication,
//            callback
//        )
//    }
//    @WorkerThread
//    private fun handleCodeExchangeResponse(
//        @Nullable tokenResponse: TokenResponse,
//        @Nullable authException: AuthorizationException?
//    ) {
//        mStateManager.updateAfterTokenResponse(tokenResponse, authException)
//        if (!mStateManager.getCurrent().isAuthorized()) {
//            val message = ("Authorization Code exchange failed"
//                    + if (authException != null) authException.error else "")
//
//            // WrongThread inference is incorrect for lambdas
//            runOnUiThread { displayNotAuthorized(message) }
//        } else {
//            runOnUiThread(this::displayAuthorized)
//        }
//    }
}