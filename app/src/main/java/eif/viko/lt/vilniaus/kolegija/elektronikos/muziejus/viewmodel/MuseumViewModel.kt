package eif.viko.lt.vilniaus.kolegija.elektronikos.muziejus.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eif.viko.lt.vilniaus.kolegija.elektronikos.muziejus.R
import eif.viko.lt.vilniaus.kolegija.elektronikos.muziejus.model.MuseumItem
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MuseumViewModel : ViewModel() {
    val museumItems: MutableState<List<MuseumItem>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            museumItems.value = loadList()
        }
    }

    fun loadList() = mutableListOf(

        MuseumItem(
            "Oscilografas S1-19A TSRS 1972 m.",
            "Analoginis - lempinis oscilografas, skirtas tirti iki 50 voltų amplitudes ir iki 1Mhz (megaherco) dažnio signalams.",
            image = R.drawable.oscilografas,
            sound =  "oscilografas"
        ),
        MuseumItem(
            "Buitinis kompiuteris ROBIK TSRS 1987 m.",
            "Tarybinis asmeninio kompiuterio ZX Spektrum klonas su vakarietišku 8 bitų ST-Z80A mikroprocesoriumi. Operacinė sistema BASIC buvo įkraunama iš kasetinio magnetofono, o vaizdas rodomas televizoriuje.",
            image = R.drawable.magnetofonas,
            sound = "robik"
        )
    )


}