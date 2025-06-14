package com.jin.jjinweather.feature.district.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.ui.state.DistrictState
import com.jin.jjinweather.ui.theme.PointColor
import com.jin.jjinweather.ui.theme.SearchBoxBackgroundColor
import kotlinx.coroutines.launch

@Composable
fun DistrictSearchScreen(viewModel: DistrictSearchViewModel, onNavigateToTemperature: () -> Unit) {
    var keyword by remember { mutableStateOf("") }
    val districtSearchListState by viewModel.districtSearchListState.collectAsState()
    val weatherListState by viewModel.weatherListState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // header
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(onNavigateToTemperature) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.tutorial_back_arrow_icon_desc),
                        tint = Color.Black
                    )
                }
                Text(
                    text = stringResource(R.string.district_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            DistrictSearchBottomSheet(
                weatherListState = weatherListState,
                districtSearchListState = districtSearchListState,
                keyword = keyword,
                onDistrictQueryChanged = {
                    keyword = it
                    viewModel.searchDistrictAt(it)
                },
                onSelectedDistrict = {
                    viewModel.saveDistrict(0, it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictSearchBottomSheet(
    weatherListState: List<CityWeather>,
    districtSearchListState: DistrictState<List<District>>,
    keyword: String,
    onDistrictQueryChanged: (keyword: String) -> Unit,
    onSelectedDistrict: (district: District) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
        when (scaffoldState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> focusRequester.requestFocus()
            SheetValue.PartiallyExpanded, SheetValue.Hidden -> focusManager.clearFocus()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 120.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetDragHandle = null, // 커스텀 핸들 사용
        sheetShadowElevation = 30.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                // 핸들
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 4.dp)
                        .background(Color.LightGray, RoundedCornerShape(2.dp))
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // 검색 박스
                // FIXME : 위치 검색 API 연동 필요
                SearchDistrictBox(
                    query = keyword,
                    focusRequester = focusRequester,
                    onQueryChange = onDistrictQueryChanged,
                    onFocusChanged = { isFocused ->
                        coroutineScope.launch {
                            if (isFocused) {
                                scaffoldState.bottomSheetState.expand()
                            } else {
                                scaffoldState.bottomSheetState.partialExpand()
                                focusManager.clearFocus()
                            }
                        }
                    }
                )
                when (districtSearchListState) {
                    is DistrictState.Idle -> Unit
                    is DistrictState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is DistrictState.Success -> {
                        val districtSearchList = districtSearchListState.data
                        LazyColumn(contentPadding = PaddingValues(vertical = 4.dp)) {
                            items(districtSearchList.size) { index ->
                                Text(
                                    text = highlightText(districtSearchList[index].address, keyword),
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .fillMaxWidth()
                                        .clickable { onSelectedDistrict(districtSearchList[index]) }
                                )
                            }
                        }
                    }

                    is DistrictState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    ) {
        // Weather 영역 리스트
        if (weatherListState.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                val defaultWeather = weatherListState.firstOrNull()
                item {
                    if (defaultWeather == null) {
                        CircularProgressIndicator()
                    } else {
                        DistrictItem(
                            isDefault = true,
                            cityName = defaultWeather.cityName,
                            weatherIconRes = defaultWeather.weather.dayWeather.icon.drawableRes,
                            temperature = defaultWeather.weather.dayWeather.temperature.toInt()
                        )
                    }
                }
                item {
                    Text(
                        text = stringResource(R.string.district_added_location),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 20.dp)
                    )
                }
                val anotherWeathers = weatherListState.filterNot { it == defaultWeather }
                items(anotherWeathers.size) {
                    val item = anotherWeathers[it]
                    DistrictItem(
                        isDefault = false,
                        cityName = item.cityName,
                        weatherIconRes = item.weather.dayWeather.icon.drawableRes,
                        temperature = item.weather.dayWeather.temperature.toInt()
                    )
                }

            }
        }
    }
}

@Composable
private fun DistrictItem(isDefault: Boolean, cityName: String, weatherIconRes: Int, temperature: Int) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(PointColor)
            .padding(
                start = 12.dp, end = 12.dp,
                top = 12.dp, bottom = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isDefault) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.dp)
                    .background(Color.White.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.NearMe,
                    contentDescription = stringResource(R.string.success_current_location_icon_desc),
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(start = 4.dp)) {
            if (isDefault) {
                Text(
                    text = stringResource(R.string.district_current_location),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 1.5.em
                )
            }
            Text(
                text = cityName,
                color = Color.White,
                fontSize = if (isDefault) 12.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.5.em
            )
        }
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(weatherIconRes),
            contentDescription = stringResource(R.string.success_current_temperature_icon_desc),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "$temperature",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun SearchDistrictBox(
    query: String,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
) {
    // FIXME : focus 되거나 텍스트 입력 시 (X) 아이콘 노출 필요.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(SearchBoxBackgroundColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.district_search_icon_desc),
            tint = Color.Gray,
            modifier = Modifier.padding(end = 8.dp)
        )

        // 입력 필드
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { onFocusChanged(it.isFocused) },
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.district_search_hint),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun highlightText(source: String, keyword: String): AnnotatedString {
    if (keyword.isBlank()) return AnnotatedString(source)
    return buildAnnotatedString {
        val keywordRegex = Regex(keyword)
        var lastIndex = 0
        keywordRegex.findAll(source).forEach { matchResult ->
            val start = matchResult.range.first
            val end = matchResult.range.last + 1

            append(source.substring(lastIndex, start))
            withStyle(style = SpanStyle(color = PointColor, fontWeight = FontWeight.Bold)) {
                append(source.substring(start, end))
            }
            lastIndex = end
        }

        if (lastIndex < source.length) {
            append(source.substring(lastIndex))
        }
    }
}
