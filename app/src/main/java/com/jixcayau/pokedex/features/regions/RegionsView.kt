package com.jixcayau.pokedex.features.regions

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.jixcayau.pokedex.R
import com.jixcayau.pokedex.composables.*
import com.jixcayau.pokedex.domain.entities.toJson
import com.jixcayau.pokedex.features.regions.composables.RegionItem
import com.jixcayau.pokedex.utils.AppSpaces
import com.jixcayau.pokedex.utils.RoutesPath

@Composable
fun RegionsView(
    navController: NavHostController,
) {
    val viewModel = remember {
        RegionsViewModel()
    }

    BaseScaffold {
        Scaffold(
            topBar = {
                Appbar(
                    onBackTap = {
                        navController.popBackStack()
                    },
                    title = stringResource(R.string.regions_appbar),
                )
            },
        ) {
            BaseBody(
                modifier = Modifier.padding(it),
                children = {

                    Label(
                        value = stringResource(R.string.regions_title),
                        type = LabelType.Subtitle,
                    )

                    VerticalSpace(AppSpaces.l)

                    for (region in viewModel.regions) {
                        RegionItem(
                            region = region,
                            onTap = {
                                navController.navigate("${RoutesPath.TeamCreateToNavigate}${region.toJson()}")
                            },
                        )
                    }
                },
            )
        }
    }
}

