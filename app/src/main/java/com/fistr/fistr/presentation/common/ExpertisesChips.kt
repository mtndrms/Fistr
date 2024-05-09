package com.fistr.fistr.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fistr.fistr.R
import com.fistr.fistr.data.model.MartialArt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpertisesChips(showTitle: Boolean, expertises: List<MartialArt>, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        if (showTitle) {
            Text(
                text = stringResource(R.string.expertises),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            expertises.forEach { martialArt ->
                SuggestionChip(
                    onClick = { },
                    label = { Text(martialArt.name) },
                    colors = SuggestionChipDefaults.suggestionChipColors().copy(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    }
}
