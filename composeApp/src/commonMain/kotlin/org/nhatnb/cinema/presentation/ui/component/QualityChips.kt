package org.nhatnb.cinema.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QualityChips(chipType: ChipType) {
    Row {
        when (chipType) {
            ChipType.SMALL -> SmallChipItem(text = "HD", selected = true)
            ChipType.MEDIUM -> MediumChipItem(text = "HD", selected = true)
            ChipType.LARGE -> MediumChipItem(text = "HD", selected = true)
        }
        Spacer(modifier = Modifier.width(6.dp))
        when (chipType) {
            ChipType.SMALL -> SmallChipItem(text = "4K", selected = false)
            ChipType.MEDIUM -> MediumChipItem(text = "4K", selected = false)
            ChipType.LARGE -> MediumChipItem(text = "4K", selected = false)
        }
    }
}

@Composable
fun SmallChipItem(text: String, selected: Boolean) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (selected) Color(0xFF1B3833) else Color.Transparent,
        border = if (selected) null else BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .padding(2.dp)
            .clickable { /* TODO */ }
    ) {
        Text(
            text = text,
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun MediumChipItem(text: String, selected: Boolean) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (selected) Color(0xFF1B3833) else Color.Transparent,
        border = if (selected) null else BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .padding(2.dp)
            .clickable { /* TODO */ }
    ) {
        Text(
            text = text,
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

enum class ChipType {
    SMALL,
    MEDIUM,
    LARGE
}