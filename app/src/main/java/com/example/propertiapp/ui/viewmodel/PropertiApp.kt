package com.example.propertiapp.ui.viewmodel

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.propertiapp.navigasi.PengelolaHalaman

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets(0)
        //topBar = {TopAppBar(scrollBehavior = scrollBehavior)}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PengelolaHalaman()
        }
    }
}