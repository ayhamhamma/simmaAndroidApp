package com.simma.simmaapp.presentation.loginMethodScreen

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


class RoundedShape() : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val bottomArcRadius = with(density) { 40.dp.toPx() } // Adjust the radius of the bottom arc
        val path = Path()
        path.apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height - bottomArcRadius)
            cubicTo(
                0f,
                size.height - bottomArcRadius,
                size.width / 2f,
                size.height + bottomArcRadius,
                size.width,
                size.height - bottomArcRadius
            )
            lineTo(size.width, 0f)
            lineTo(0f, 0f)

            close()
        }
        return Outline.Generic(path)
    }


}