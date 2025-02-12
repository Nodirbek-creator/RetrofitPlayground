package com.example.retrofitnagibation.ui.user_interface

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.retrofitnagibation.model.Login
import com.example.retrofitnagibation.model.User
import com.example.retrofitnagibation.network.ApiService
import com.example.retrofitnagibation.ui.theme.myBlue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*Todo: For now, the Ui of the login is simple. However, we will make more complex in the progress*/
//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
@Composable
fun Auth(
    navController: NavHostController,
    apiService: ApiService
) {
    //query variables
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", fontSize = 24.sp, fontWeight = FontWeight.W700)
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "user",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Gray
                )
            },
            label = {
                Text(
                    text = "Username",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            },
            placeholder = {
                Text(
                    text = "Username..",
                    fontSize = 18.sp,
                    color = Color.LightGray
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            leadingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "password",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Gray
                    )
                }
            },
            label = {
                Text(
                    text = "Username",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = "Username..",
                    fontSize = 18.sp,
                    color = Color.LightGray
                )
            }
        )
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                /*todo: -> navigate to next screen if login is successful*/
                /*api call*/
                apiService.login(Login(username, password))
                    .enqueue(object : Callback<User> {
                        override fun onResponse(
                            call: Call<User?>,
                            response: Response<User?>
                        ) {
                            if (response.isSuccessful){
                                navController.navigate("home")
                            }
                            Log.d("onResponse:", "${response.body()?.username}")
                        }

                        override fun onFailure(
                            call: Call<User?>,
                            t: Throwable
                        ) {
                            isError = true
                            Log.d("onFailure:", "${t.message}")
                        }
                    })
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = myBlue,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(0.7f).height(48.dp)
        ) {
            Text(
                text = "Login",
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
            )
        }

    }

    if(isError){
        AlertDialog(
            onDismissRequest = {isError = false},
            title = { Text("Error") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Red,
                )
            },
            confirmButton = {
                Button(
                    onClick = {}
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {}
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}