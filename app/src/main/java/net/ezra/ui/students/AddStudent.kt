package net.ezra.ui.students


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter

import coil.request.ImageRequest
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


@ExperimentalMaterial3Api
@Composable
fun AddStudents(navController: NavHostController) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
    ){
        item {
            Column(

                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxSize()
                    .background(Color(0xff26231f)),


                horizontalAlignment = Alignment.CenterHorizontally,

                ){

                Text(text = "Register Student", color = Color.White)

                var photoUri: Uri? by remember { mutableStateOf(null) }
                val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    photoUri = uri
                }

                var studentName by rememberSaveable {
                    mutableStateOf("")
                }

                var studentClass by rememberSaveable {
                    mutableStateOf("")
                }

                var location by rememberSaveable {
                    mutableStateOf("")
                }

                var admissionnumber by rememberSaveable {
                    mutableStateOf("")
                }

                var gender by rememberSaveable {
                    mutableStateOf("")
                }




                OutlinedTextField(
                    value = studentName ,
                    onValueChange = { studentName = it },
                    label = { Text(text = "NAME") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor =Color.White,
                        unfocusedLabelColor =Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,



                        )
                )

                OutlinedTextField(
                    value = studentClass ,
                    onValueChange = { studentClass= it },
                    label = { Text(text = "CLASS") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor =Color.White,
                        unfocusedLabelColor =Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,



                        )
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location= it },
                    label = { Text(text = "LOCATION") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor =Color.White,
                        unfocusedLabelColor =Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,



                        )
                )

                OutlinedTextField(
                    value = admissionnumber,
                    onValueChange = { admissionnumber= it },
                    label = { Text(text = "ADM NO:") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(Color.White),
//            keyboardOptions = KeyboardOptions
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor =Color.White,
                        unfocusedLabelColor =Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,



                        )
                )

                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender= it },
                    label = { Text(text = "GENDER") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor =Color.White,
                        unfocusedLabelColor =Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,



                        )
                )








                OutlinedButton(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                //Here we request only photos. Change this to .ImageAndVideo if you want videos too.
                                //Or use .VideoOnly if you only want videos.
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Text("Select Image", color = Color.White)
                }


                if (photoUri != null) {
                    //Use Coil to display the selected image
                    val painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = photoUri)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(150.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray),
                        contentScale = ContentScale.Crop,

                        )
                }


                OutlinedButton(onClick = {
                    photoUri?.let { uploadImageToFirebaseStorage(it, studentName, studentClass, location, admissionnumber, gender) }

                }) {

                    Text(text = "Register", color = Color.White)


                }











            }
        }
    }




}



fun uploadImageToFirebaseStorage(imageUri: Uri, studentName: String, studentClass: String, location:String, admissionnumber:String, gender:String) {
    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("images/${UUID.randomUUID()}")

    val uploadTask = imageRef.putFile(imageUri)
    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result
            saveToFirestore(downloadUri.toString(), studentName, studentClass, location, admissionnumber, gender)
        } else {


        }
    }
}

fun saveToFirestore(imageUrl: String, studentName: String, studentClass: String, location:String, admissionnumber:String, gender:String) {
    val db = Firebase.firestore
    val imageInfo = hashMapOf(
        "imageUrl" to imageUrl,
        "studentName" to studentName,
        "studentClass" to studentClass,
        "location" to location,
        "admissionnumber" to admissionnumber,
        "gender" to gender


       
    )
    



    db.collection("Students")
        .add(imageInfo)
        .addOnSuccessListener {
          


        }
        .addOnFailureListener {
            // Handle error
        }
}







@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewLight() {
    AddStudents(rememberNavController())
}




