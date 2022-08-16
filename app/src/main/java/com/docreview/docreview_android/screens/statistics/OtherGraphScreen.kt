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
    5929, 6898, 8961, 5674, 7122, 6592, 3427, 5520, 4680, 7418,
    11115, 8386, 12450, 10411, 10852, 7782, 7371, 4983, 9234, 6847,
    4743, 4080, 3611, 7295, 9900, 12438, 11186, 5439, 4227, 5138,
)

@Composable
fun OtherGraphpage(navController: NavHostController) {
    Column {
        HeaderTextOther()
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

                    lineChartData().forEachIndexed { index, data ->
                        if (lineChartData().size >= index + 2) {
                            val y0 = (maxValue - data) * (size.height / maxValue)
                            val x0 = currentX + distance
                            points.add(PointF(x0, y0))
                            currentX += distance
                        }
                    }

                    for (i in 0 until points.size - 1) {
                        drawLine(
                            start = Offset(points[i].x, points[i].y),
                            end = Offset(points[i + 1].x, points[i + 1].y),
                            color = Color.Blue,
                            strokeWidth = 8f
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderTextOther() {
    Text(
        text = "OthersGraph",
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp, start = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 35.sp
    )
}