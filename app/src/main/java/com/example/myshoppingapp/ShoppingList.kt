package com.example.myshoppingapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshoppingapp.ui.theme.MyShoppingAppTheme

data class ShoppingItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val isEditing: Boolean = false
)

@Composable
fun ShoppingListApp() {
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 80.dp)
            ) {
                Text("Add Item")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(sItems) { item ->
                    if (item.isEditing) {
                        ShoppingItemEditor(
                            item = item,
                            onEditComplete = { editedName, editedQuantity ->
                                sItems = sItems.map {
                                    if (it.id == item.id) {
                                        it.copy(name = editedName, quantity = editedQuantity, isEditing = false)
                                    } else {
                                        it
                                    }
                                }
                            })
                    } else {
                        ShoppingListItem(
                            item,
                            {
                                // finding out which item we are editing and changing is "isEditing boolean" to true
                                sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
                            },
                            {
                                sItems = sItems - item
                            }
                        )


                    }
                }

            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            if (itemName.isNotBlank()) {
                                val newItem =
                                    ShoppingItem(
                                        id = (sItems.maxOfOrNull { it.id } ?: 0) + 1,
                                        name = itemName,
                                        quantity = itemQuantity.toIntOrNull() ?: 1
                                    )
                                sItems = sItems + newItem
                            }
                            showDialog = false
                            itemName = ""
                            itemQuantity = ""
                        }) {
                            Text("Add")
                        }
                        Button(onClick = {
                            showDialog = false
                            itemName = ""
                            itemQuantity = ""
                        }) {
                            Text("Cancel")
                        }
                    }
                },
                title = { Text(" Add shopping Item") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("Item Name") }
                        )

                        OutlinedTextField(
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("Quantity") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                }

            )

        }

    }


}

@Composable

fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,

    ) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = " Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }

    }

}


@Composable
fun ShoppingItemEditor(item: ShoppingItem, onEditComplete: (String, Int) -> Unit) {
    var editName by remember { mutableStateOf(item.name) }
    var editQuantity by remember { mutableStateOf(item.quantity.toString()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            BasicTextField(
                value = editName,
                onValueChange = { editName = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            BasicTextField(
                value = editQuantity,
                onValueChange = { editQuantity = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }

        Button(
            onClick = {
                onEditComplete(editName, editQuantity.toIntOrNull() ?: 1)
            }
        ) {
            Text("Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    MyShoppingAppTheme {
        ShoppingListApp()
    }
}
