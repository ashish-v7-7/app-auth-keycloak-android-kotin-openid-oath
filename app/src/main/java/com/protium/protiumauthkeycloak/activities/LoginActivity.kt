package com.protium.protiumauthkeycloak.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.protium.protiumauthkeycloak.*
import com.protium.protiumauthkeycloak.R
import net.openid.appauth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String
import net.openid.appauth.AuthorizationException

import net.openid.appauth.TokenResponse

import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationService.TokenResponseCallback
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.AuthorizationServiceConfiguration.RetrieveConfigurationCallback


class LoginActivity : AppCompatActivity() {
    private var username: EditText? = null
    private var password: EditText? = null
    private var btnLogin: Button? = null
    private var btnLoginWithGoogle: Button? = null
    private var textView: TextView? = null

    private val RC_AUTH = 100
    private var mAuthService: AuthorizationService? = null
    private var mStateManager: AuthStateManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnLogin = findViewById(R.id.button_login)
        btnLoginWithGoogle = findViewById(R.id.button_google)
        textView = findViewById(R.id.textView)

        mStateManager = AuthStateManager.getInstance(this)
        mAuthService = AuthorizationService(this)



//        if (mStateManager?.current?.isAuthorized!!) {
//            Log.d("Auth", "Done")
//            btnLogin?.setText("Logout")
//            mStateManager?.current?.performActionWithFreshTokens(
//                mAuthService!!
//            ) { accessToken, idToken, exception ->
////                ProfileTask().execute(accessToken)
//            }
//        }



        btnLogin?.setOnClickListener(View.OnClickListener { //todo: code here
            getAccessToken()
        })
        btnLoginWithGoogle?.setOnClickListener(View.OnClickListener { //todo: code here
            continueWithGoogle()
        })
    }

    private fun getAccessToken(){
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("http://192.168.1.9:8080/auth/realms/protium/protocol/openid-connect/auth"), // authorization endpoint
            Uri.parse("http://192.168.1.9:8080/auth/realms/protium/protocol/openid-connect/token"), // token endpoint
            Uri.parse("http://192.168.1.9:8080/auth/realms/protium/clients-registrations/openid-connect") )

        val clientId = "rest client"
        val redirectUri = Uri.parse("consumecode://https")
        val builder = AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            ResponseTypeValues.CODE,
            redirectUri
        )
        builder.setScopes("openid profile")

        val authRequest = builder.build()



        val authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        startActivityForResult(authIntent, RC_AUTH)

    }
    private fun continueWithGoogle(){
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://accounts.google.com/o/oauth2/v2/auth"), // authorization endpoint
            Uri.parse("https://oauth2.googleapis.com/token") // token endpoint
 )

        val clientId = "211490619112-22u739pje7e5dv7gcd14458sjft75bb9.apps.googleusercontent.com"
        val redirectUri = Uri.parse("urn:ietf:wg:oauth:2.0:oob")
        val builder = AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            ResponseTypeValues.CODE,
            redirectUri
        )
        builder.setScopes("openid profile")

        val authRequest = builder.build()


        /*  val intentBuilder = mAuthService?.createCustomTabsIntentBuilder(authRequest.toUri())
        intentBuilder?.setToolbarColor(ContextCompat.getColor(this, R.color.colorAccent))
        customIntent = intentBuilder?.build()*/

        val authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        startActivityForResult(authIntent, RC_AUTH)

    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    Log.e("sdf","sdf")
    if (requestCode == RC_AUTH) {
        Log.e("sdf","sdf")
        val resp = AuthorizationResponse.fromIntent(data!!)
        val ex = AuthorizationException.fromIntent(data)
        Log.e("sdf",resp.toString())
        if (resp != null) {
            Log.e("sdf","sdf")

            mAuthService = AuthorizationService(this)
            mStateManager?.updateAfterAuthorization(resp, ex)
            textView?.text = resp.authorizationCode.toString()


//            runOnUiThread {
//                mAuthService?.performTokenRequest(
//                    resp.createTokenExchangeRequest()
//                ) { resp2, ex2 ->
//                    Log.e("sdf", "sdf")
//                    if (resp2 != null) {
//                        //mStateManager?.updateAfterTokenResponse(resp2, ex2)
//                        Log.e("sdf", "sdf")
//                        //btnLogin?.setText("Logout")
//                        resp2.accessToken?.let { Log.d("accessToken", it) }
//                        //   ProfileTask().execute(resp.accessToken)
//                    } else {
//                        // authorization failed, check ex for more details
//                    }
//                }
//            }

            // authorization completed
        } else {
            // authorization failed, check ex for more details
        }
        // ... process the response or exception ...
    } else {
        // ...
    }

}

//inner class ProfileTask : AsyncTask<kotlin.String?, Void, JSONObject>() {
//    override fun doInBackground(vararg tokens: kotlin.String?): JSONObject? {
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("https://www.googleapis.com/oauth2/v3/userinfo")
//            .addHeader("Authorization", kotlin.String.format("Bearer %s", tokens[0]))
//            .build()
//        try {
//            val response = client.newCall(request).execute()
//            val jsonBody: kotlin.String = response.body()!!.string()
//            Log.i("LOG_TAG", kotlin.String.format("User Info Response %s", jsonBody))
//            return JSONObject(jsonBody)
//        } catch (exception: Exception) {
//            Log.w("LOG_TAG", exception)
//        }
//        return null
//    }
//    override fun onPostExecute(userInfo: JSONObject?) {
//        if (userInfo != null) {
//            val fullName = userInfo.optString("name", null)
//            val imageUrl =
//                userInfo.optString("picture", null)
//            if (!TextUtils.isEmpty(imageUrl)) {
//                Glide.with(this@LoginActivity).load(imageUrl).into(profile!!);
//            }
//            if (!TextUtils.isEmpty(fullName)) {
//                username?.setText(fullName)
//            }
//            val message = if (userInfo.has("error")) { java.lang.String.format(
//                "%s [%s]", getString(net.openid.appauth.R.string.request_failed), userInfo.optString("error_description", "No description"))
//            } else {
//                getString(net.openid.appauth.R.string.request_complete)
//            }
//            Snackbar.make(profile!!, message, Snackbar.LENGTH_SHORT).show()
//        }
//    }
//}
}