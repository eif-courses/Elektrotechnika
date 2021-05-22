package eif.viko.lt.vilniaus.kolegija.elektronikos.muziejus

import android.content.res.Resources
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eif.viko.lt.vilniaus.kolegija.elektronikos.muziejus.model.MuseumItem
import eif.viko.lt.vilniaus.kolegija.elektronikos.muziejus.ui.theme.ElektrotechnikaTheme
import eif.viko.lt.vilniaus.kolegija.elektronikos.muziejus.viewmodel.MuseumViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.math.absoluteValue


class MainActivity : ComponentActivity() {
  private val viewModel by viewModels<MuseumViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ElektrotechnikaTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          Scaffold(
            topBar = { TopBar(stringResource(R.string.app_name)) }
          ) {
            MessageList(viewModel.museumItems.value)
          }
        }
      }
    }
  }
}


@Composable
fun TopBar(title: String) {

  TopAppBar(title = {
    Text(title)
  })


}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}


@Composable
fun PlaySound(source: Int) {
  val playAudioState: Int by rememberSaveable { mutableStateOf(R.raw.oscilografas) }

}

@Composable
fun VideoPlayer() {
  // This is the official way to access current context from Composable functions


  // Do not recreate the player everytime this Composable commits


  // Gateway to legacy Android Views through XML inflation.
//    AndroidView(resId = R.layout.surface) {
//        val exoPlayerView = it.findViewById<PlayerView>(R.id.player_view)
//
//        exoPlayerView.player = exoPlayer
//        exoPlayer.playWhenReady = true
//    }
}


@Composable
fun MuseumCard(museumItem: MuseumItem) {

  val context = LocalContext.current

 // TODO ATKOMENTUOTI KAI BUS PRODUCTION READY
//  val id = context.resources.getIdentifier(museumItem.sound, "raw", context.packageName);
//  val mediaPlayer = MediaPlayer.create(context, id)
 val stateMedia = remember { mutableStateOf(true) }
  val togglePlay = {
    stateMedia.value = !stateMedia.value
//    if (!stateMedia.value) {
//      mediaPlayer.start()
//    } else {
//      mediaPlayer.pause()
//    }
  }


  val isEnabled = remember { mutableStateOf(true) }
  val isCollapsed = remember { mutableStateOf(true) }


  val size: Dp by animateDpAsState(
    targetValue = if (isCollapsed.value) 100.dp else 300.dp,
    animationSpec = tween(
      durationMillis = 2000, // duration
      easing = FastOutSlowInEasing
    ),
    finishedListener = {
      // disable the button
      isEnabled.value = true
    }
  )




  Text(
    museumItem.name,
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colors.primary,
    modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
  )

  Row(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.size(100.dp),
    ) {
      // Image goes here
      ImageFunction(image = museumItem.image)
    }
    Column(
      modifier = Modifier
          .padding(start = 8.dp)
          .align(Alignment.CenterVertically)
    ) {
      Text(
        museumItem.description,
        style = MaterialTheme.typography.body2,
        modifier = Modifier.fillMaxWidth()
      )

    }

  }

  Divider(color = androidx.compose.ui.graphics.Color.LightGray)
  Row(modifier = Modifier.fillMaxSize()) {
    Column(Modifier.fillMaxSize()) {
      IconButton(
        onClick = togglePlay
      ) {
        if (stateMedia.value) {
          Icon(
            Icons.Filled.PlayArrow,
            contentDescription = stringResource(id = R.string.app_name),
            tint = colorResource(id = R.color.green),
            modifier = Modifier.size(size),
          )
        } else {
          Icon(
            painterResource(id = R.drawable.ic_baseline_pause_24),
            contentDescription = stringResource(id = R.string.app_name),
            tint = colorResource(id = R.color.green),
            modifier = Modifier.size(size),
          )
        }

      }
    }
  }
  Divider(color = androidx.compose.ui.graphics.Color.LightGray)

}


@Composable
fun MessageList(museumList: List<MuseumItem>) {
  LazyColumn {
    items(museumList) {
      MuseumCard(museumItem = it)
    }
  }
}

@Composable
fun ImageFunction(image: Int) {
  Image(
    painter = painterResource(image),
    contentDescription = null,
    modifier = Modifier
        .height(200.dp)
        .fillMaxWidth()
        .padding(top = 5.dp)
        .clip(shape = RoundedCornerShape(4.dp)),
    contentScale = ContentScale.Inside
  )
  Spacer(Modifier.height(16.dp))
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

  ElektrotechnikaTheme {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
      Scaffold(
        topBar = { TopBar(stringResource(R.string.app_name)) }
      ) {
        MessageList(
          listOf(
            MuseumItem(
              "Oscilografas S1-19A TSRS 1972 m.",
              "Analoginis - lempinis oscilografas, skirtas tirti iki 50 voltų amplitudes ir iki 1Mhz (megaherco) dažnio signalams.",
              image = R.drawable.oscilografas,
              sound = "oscilografas"
            ),
            MuseumItem(
              "Buitinis kompiuteris ROBIK TSRS 1987 m.",
              "Tarybinis asmeninio kompiuterio ZX Spektrum klonas su vakarietišku 8 bitų ST-Z80A mikroprocesoriumi. Operacinė sistema BASIC buvo įkraunama iš kasetinio magnetofono, o vaizdas rodomas televizoriuje.",
              image = R.drawable.magnetofonas,
              sound = "robik"
            ),
            MuseumItem(
              "Buitinis kompiuteris ROBIK TSRS 1987 m.",
              "Tarybinis asmeninio kompiuterio ZX Spektrum klonas su vakarietišku 8 bitų ST-Z80A mikroprocesoriumi. Operacinė sistema BASIC buvo įkraunama iš kasetinio magnetofono, o vaizdas rodomas televizoriuje.",
              image = R.drawable.magnetofonas,
              sound = "oscilografas"
            ),
            MuseumItem(
              "Buitinis kompiuteris ROBIK TSRS 1987 m.",
              "Tarybinis asmeninio kompiuterio ZX Spektrum klonas su vakarietišku 8 bitų ST-Z80A mikroprocesoriumi. Operacinė sistema BASIC buvo įkraunama iš kasetinio magnetofono, o vaizdas rodomas televizoriuje.",
              image = R.drawable.magnetofonas,
              sound = "oscilografas"
            )
          )
        )
      }
    }
  }
}