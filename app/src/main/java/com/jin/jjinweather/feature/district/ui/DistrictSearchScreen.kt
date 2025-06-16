package com.jin.jjinweather.feature.district.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.jin.jjinweather.feature.district.ui.content.DistrictSearchBottomSheet
import com.jin.jjinweather.feature.district.ui.content.DistrictSearchHeader
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictSearchScreen(viewModel: DistrictSearchViewModel, onNavigateToTemperature: () -> Unit) {
    var keyword by remember { mutableStateOf("") }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val districtSearchListState by viewModel.districtSearchListState.collectAsState()
    val weatherList by viewModel.weatherListState.collectAsState()

    LaunchedEffect(keyword) {
        viewModel.searchDistrictAt(keyword)
    }

    BackHandler {
        coroutineScope.launch {
            when (scaffoldState.bottomSheetState.currentValue) {
                SheetValue.Expanded -> scaffoldState.bottomSheetState.partialExpand()
                else -> onNavigateToTemperature()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            DistrictSearchHeader(onNavigateToTemperature)
            DistrictSearchBottomSheet(
                weatherList = weatherList,
                districtSearchListState = districtSearchListState,
                keyword = keyword,
                scaffoldState = scaffoldState,
                focusRequester = focusRequester,
                focusManager = focusManager,
                coroutineScope = coroutineScope,
                onDistrictQueryChanged = { keyword = it },
                onSelectedDistrict = {
                    viewModel.saveDistrict(weatherList.size, it)
                },
                onBottomSheetDismissRequest = {
                    keyword = ""
                    focusManager.clearFocus()
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                },
                onQueryClear = { keyword = "" }
            )
        }
    }
}
