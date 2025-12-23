package com.example.ca2_cse224

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ca2_cse224.ui.theme.CA2_CSE224Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA2_CSE224Theme {
                SignupPage()
            }
        }
    }
}

@Composable
fun SignupPage() {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("") }
    var isDarkMode by remember { mutableStateOf(false) }
    val backgroundColor = if (isDarkMode) Color.Black else Color.White
    val contentColor = if (isDarkMode) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Signup Form",
            style = MaterialTheme.typography.headlineSmall,
            color = contentColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                Log.d("SignupForm", "Username: $username")
            },
            label = { Text("Username") },
            placeholder = { Text("Enter Username") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                Log.d("SignupForm", "Password: $password")
            },
            label = { Text("Password") },
            placeholder = { Text("Enter Password") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("User Type: ", color = contentColor)
            RadioButton(
                selected = userType == "Student",
                onClick = {
                    userType = "Student"
                    Log.d("SignupForm", "User Type: $userType")
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = if (isDarkMode) Color.White else Color.Black
                )
            )
            Text("Student", color = contentColor)
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = userType == "Admin",
                onClick = {
                    userType = "Admin"
                    Log.d("SignupForm", "User Type: $userType")
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = if (isDarkMode) Color.White else Color.Black
                )
            )
            Text("Admin", color = contentColor)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Dark Mode: ", color = contentColor)
            Switch(
                checked = isDarkMode,
                onCheckedChange = {
                    isDarkMode = it
                    Log.d("SignupForm", "Dark Mode: $isDarkMode")
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (username.length < 4) {
                    Toast.makeText(context, "Username must be more than 4 characters", Toast.LENGTH_SHORT).show()
                    Log.d("SignupForm", "Validation Failed: Username too short")
                } else if (password.length < 6) {
                    Toast.makeText(context, "Password must be more than 6 characters", Toast.LENGTH_SHORT).show()
                    Log.d("SignupForm", "Validation Failed: Password too short")
                } else if (userType.isEmpty()) {
                    Toast.makeText(context, "Please select a User Type", Toast.LENGTH_SHORT).show()
                    Log.d("SignupForm", "Validation Failed: No user type selected")
                } else if (username != "batsaikhan") {
                    Toast.makeText(context, "Invalid Username", Toast.LENGTH_SHORT).show()
                    Log.d("SignupForm", "Validation Failed: Invalid username")
                } else if (password != "12327076") {
                    Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show()
                    Log.d("SignupForm", "Validation Failed: Invalid password")
                } else {
                    Toast.makeText(context, "Signup Successful!", Toast.LENGTH_LONG).show()
                    Log.d("SignupForm", "Signup Successful - Username: $username, User Type: $userType, Dark Mode: $isDarkMode")
                    val intent = Intent(context, WelcomeActivity::class.java).apply {
                        putExtra("USERNAME", username)
                        putExtra("USER_TYPE", userType)
                    }
                    context.startActivity(intent)
                }
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Signup")
        }
    }
}

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("USERNAME") ?: ""
        val userType = intent.getStringExtra("USER_TYPE") ?: ""
        Log.d("WelcomeActivity", "Received - Username: $username, User Type: $userType")
        setContent {
            CA2_CSE224Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Welcome!",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Username: $username")
                        Text("User Type: $userType")
                    }
                }
            }
        }
    }
}
