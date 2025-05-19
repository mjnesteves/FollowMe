package com.followme.ui.screens.home.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults


import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.followme.R
import com.followme.ui.screens.home.HomeViewModel
import com.followme.ui.theme.AccentColor
import com.followme.ui.theme.Primary
import com.followme.ui.theme.Secondary


@Composable
fun AppToolbar(
    userName: String, logoutButtonClicked: () -> Unit,
    navigationIconClicked: () -> Unit
) {

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 50.dp, start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(20.dp)),

        backgroundColor = Primary,

        title = {
            Text(
                text = userName,
                color = Color.Black,
                modifier = Modifier
                    //.border(1.dp, Color.Black, RoundedCornerShape(20))
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        },
        navigationIcon = {
            IconButton(onClick = {
                navigationIconClicked.invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.menu),
                    tint = Color.Black
                )
            }

        },
        actions = {
            IconButton(onClick = {
                logoutButtonClicked.invoke()
            }) {
                Image(
                    painter = painterResource(id= R.drawable.logout),
                    contentDescription = stringResource(id= R.string.logout)
                )
            }
        }
    )
}


@Composable
fun BotaoAdicionar(navegar: () -> Unit) {
    FloatingActionButton(
        containerColor = Primary,
        onClick = { navegar() },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}


@Composable
fun CenteredBottomAppBar(navegar: () -> Unit) {
    BottomAppBar(
        modifier = Modifier
            .padding(bottom = 60.dp),
        backgroundColor = Color.Transparent, // Transparent background
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Center

        ) {
            CenteredNavigationFab(navegar)
        }

    }
}

@Composable
fun CenteredNavigationFab(navegar: () -> Unit) {
    FloatingActionButton(
        containerColor = Primary,
        onClick = {
            navegar()
        },
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Navigate"
        )
    }
}


@Composable
fun BotaoAplicacoes(
    value: String,
    navegar: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(70.dp)
            .shadow(20.dp),
        onClick = {
            navegar()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(20.dp),

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(70.dp)
                .background(
                    color = Primary,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Center


        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

        }

    }
}






@Composable
fun NavigationDrawerHeader(value: String?) {
    Box(
        modifier = Modifier
            .background(
                Brush.linearGradient(
                    listOf(Primary, Secondary)
                )
            )
            .fillMaxWidth()
            //.border(1.dp, Color.Black, RoundedCornerShape(20))
            .height(150.dp)
            .wrapContentHeight()
            .wrapContentSize(Center)

    ) {

        NavigationDrawerText(
            title = value?: stringResource(R.string.navigation_header), 28.sp , AccentColor
        )

    }
}

@Composable
fun NavigationDrawerBody(
    navigationDrawerItems: List<HomeViewModel.NavigationItem>,
    onNavigationItemClicked: (HomeViewModel.NavigationItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        items(navigationDrawerItems) {
            NavigationItemRow(item = it,onNavigationItemClicked)
        }

    }
}

@Composable
fun NavigationItemRow(
    item: HomeViewModel.NavigationItem,
    onNavigationItemClicked: (HomeViewModel.NavigationItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigationItemClicked.invoke(item)
            }
            .padding(all = 16.dp)
    ) {

        Icon(
            imageVector = item.icon,
            contentDescription = item.description,
        )

        Spacer(modifier = Modifier.width(18.dp))

        NavigationDrawerText(title = item.title, 18.sp, Primary)


    }
}

@Composable
fun NavigationDrawerText(title: String, textUnit: TextUnit, color: Color) {

    val shadowOffset = Offset(4f, 6f)

    Text(
        text = title,
        style = TextStyle(
            color = Color.Black,
            fontSize = textUnit,
            fontStyle = FontStyle.Normal,
            textAlign =  TextAlign.Center  ,
            shadow = Shadow(
                color = Primary,
                offset = shadowOffset, 2f
            )
        )
    )
}












