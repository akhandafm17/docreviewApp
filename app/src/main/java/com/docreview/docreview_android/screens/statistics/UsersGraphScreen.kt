package com.docreview.docreview_android.screens


import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

private fun lineChartData() = listOf(
    0, 5, 5, 8, 8, 9, 9, 10, 10, 14, 14
)

@Composable
fun UsersGraphpage(navController: NavHostController) {
    Column() {
        HeaderTextGraph()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(16.dp),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(align = Alignment.BottomStart)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    val distance = size.width / (lineChartData().size + 1)
                    var currentX = 0F
                    val maxValue = lineChartData().maxOrNull() ?: 0
                    val points = mutableListOf<PointF>()
                    val numbers = mutableListOf<Number>()

                    lineChartData().forEachIndexed { index, data ->
                        if (lineChartData().size >= index) {
                            val y0 = (maxValue - data) * (size.height / maxValue)
                            val x0 = currentX + distance
                            points.add(PointF(x0, y0))
                            numbers.add(data)
                            currentX += distance
                        }
                    }

                    drawLine(
                        start = Offset(10f, 550f),
                        end = Offset(940f, 550f),
                        color = Color.Black,
                        strokeWidth = 5f
                    )

                    drawLine(
                        start = Offset(10f, 550f),
                        end = Offset(10f, -200f),
                        color = Color.Black,
                        strokeWidth = 5f
                    )

                    for (i in 0 until points.size - 1) {
                        drawLine(
                            start = Offset(points[i].x, points[i].y),
                            end = Offset(points[i + 1].x, points[i + 1].y),
                            color = Color.Magenta,
                            strokeWidth = 8f
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderTextGraph() {
    Text(
        text = "UsersGraph",
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp, start = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 35.sp
    )
}