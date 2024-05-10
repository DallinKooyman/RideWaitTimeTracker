package com.dallinkooyman.disneyridetimecomparison.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme

@Composable
fun ConfirmDialog(
    dialogTitle: String,
    supportingText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
){
    Dialog(
        onDismissRequest = {onDismiss()},
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
    ){
        Card {
            Column {
                Text(
                    text = dialogTitle,
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
                Text (
                    text = supportingText,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp ,end =  10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onConfirm() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ConfirmDialogPreview() {
    AppTheme {
        ConfirmDialog(
            dialogTitle = "At Interactable?",
            supportingText = "You have reached the interactable? Confirm is so",
            onDismiss = { /*TODO*/ }) {
        }
    }
}