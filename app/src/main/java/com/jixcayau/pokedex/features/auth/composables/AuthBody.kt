package com.jixcayau.pokedex.features.auth.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jixcayau.pokedex.R
import com.jixcayau.pokedex.composables.BaseBody
import com.jixcayau.pokedex.composables.BaseScaffold
import com.jixcayau.pokedex.composables.Clickable
import com.jixcayau.pokedex.composables.HorizontalSpace
import com.jixcayau.pokedex.composables.Label
import com.jixcayau.pokedex.composables.LabelType
import com.jixcayau.pokedex.composables.VerticalSpace
import com.jixcayau.pokedex.extensions.splashBackground
import com.jixcayau.pokedex.ui.theme.Colors
import com.jixcayau.pokedex.utils.AppSpaces

@Composable
fun AuthBody(
    isLoading: Boolean,
    onBackTap: (() -> Unit)?,
    actionTitle: String,
    actionButtonText: String,
    actionOnTap: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    BaseScaffold(
        isLoading = isLoading,
    ) {
        Scaffold(
            backgroundColor = Color.Transparent,
            modifier = Modifier.splashBackground(),
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    contentColor = Colors.White,
                    elevation = AppSpaces.zero.dp,
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            onBackTap.let {
                                if (it == null) {
                                    VerticalSpace(AppSpaces.zero)
                                } else {
                                    Clickable(
                                        onTap = it,
                                        child = {
                                            Icon(
                                                Icons.Default.ArrowBack,
                                                contentDescription = "stringResource(id = R.string.shopping_cart_content_desc)",
                                            )
                                        },
                                    )
                                }
                            }

                            TextButton(
                                onClick = actionOnTap,
                                content = {
                                    Text(
                                        actionTitle,
                                        color = Colors.White,
                                        fontWeight = FontWeight.Normal,

                                        )

                                    HorizontalSpace(AppSpaces.xxs2)

                                    Text(
                                        actionButtonText,
                                    )
                                },
                            )
                        }
                    },
                )
            },
        ) {
            Column(
                Modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VerticalSpace(AppSpaces.xl)

                Label(
                    value = stringResource(R.string.app_name_with_author),
                    type = LabelType.Title,
                    color = Colors.White,
                )

                VerticalSpace(AppSpaces.xxl4)

                BaseBody(
                    modifier = Modifier
                        .padding(
                            horizontal = AppSpaces.s.dp,
                        )
                        .clip(
                            RoundedCornerShape(
                                topEnd = AppSpaces.l.dp,
                                topStart = AppSpaces.l.dp,
                            )
                        )
                        .background(Colors.PrimaryColor)
                        .padding(
                            horizontal = AppSpaces.m.dp,
                        ),
                    color = Color.Transparent,
                    children = content,
                )
            }
        }
    }
}