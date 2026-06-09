# LyraApp - Tipografi Sistemi

> Bu dosya LyraApp uygulamasinin tipografi sistemi icin
> **tek dogruluk kaynagidir** (single source of truth).
> Android Jetpack Compose Material 3 `Typography` yapisi esas alinir.

---

## 1. Temel Kural

LyraApp icindeki ekranlarda metin stilleri dogrudan `TextStyle(...)`,
`FontFamily(...)`, `fontSize`, `fontWeight` veya benzeri ham degerlerle
daginik sekilde tanimlanmaz.

Metin stilleri daima asagidaki yapi uzerinden okunur:

```kotlin
MaterialTheme.typography.<slot>
```

Ornek:

```kotlin
Text(
    text = "LyraApp",
    style = MaterialTheme.typography.titleLarge,
)
```

`TextStyle(...)` tanimlari yalnizca `Type.kt` icinde merkezi tipografi
sistemi kurulurken kullanilir.

---

## 2. Font Ailesi Karari

LyraApp icin bu asamada yeni bir font bagimliligi eklenmeyecektir.

Varsayilan font ailesi:

```kotlin
FontFamily.Default
```

Bu karar, uygulamanin erken asamada ek font dosyasi, lisans yonetimi veya
gereksiz bagimlilik tasimamasi icin alinmistir.

Ileride marka fontu netlestirilirse font ailesi merkezi olarak `Type.kt`
dosyasinda degistirilir. Ekranlar `MaterialTheme.typography.<slot>` uzerinden
stil okudugu icin ekran bazli toplu degisiklik gerekmemelidir.

---

## 3. Material 3 Typography Rolleri

LyraApp tipografi sistemi Material 3 rollerini kullanir. Roller ekran
hiyerarsisine gore secilir.

| Slot | Kullanim Amaci |
|---|---|
| `displayLarge` | Cok nadir kullanilan, en buyuk vurgu metni. |
| `displayMedium` | Ozel tanitim veya bos durum gibi yuksek vurgu alanlari. |
| `displaySmall` | Sayfa icinde cok guclu vurgu gereken basliklar. |
| `headlineLarge` | Ana ekran veya ana bolum basliklari. |
| `headlineMedium` | Ikincil sayfa basliklari. |
| `headlineSmall` | Kompakt ekran basliklari. |
| `titleLarge` | Kart, panel veya detay basliklari. |
| `titleMedium` | Liste ogesi, bolum veya form basliklari. |
| `titleSmall` | Kucuk alt basliklar ve yogun arayuz basliklari. |
| `bodyLarge` | Ana okuma metni. |
| `bodyMedium` | Standart aciklama, liste ve kart metinleri. |
| `bodySmall` | Yardimci aciklama ve ikincil metinler. |
| `labelLarge` | Birincil buton ve onemli etiketler. |
| `labelMedium` | Sekme, filtre, chip veya kucuk kontrol etiketleri. |
| `labelSmall` | En kucuk yardimci etiketler. |

---

## 4. Type.kt Kod Yapisi

`Type.kt` dosyasi tipografi sisteminin uygulama kodundaki merkezi karsiligidir.
Bu dosyada `Typography` nesnesi tanimlanir ve `Theme.kt` icinde
`MaterialTheme`'e verilir.

Onerilen paket:

```kotlin
package com.turkcell.lyraapp.ui.theme
```

Onerilen kod yapisi:

```kotlin
package com.turkcell.lyraapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)
```

Bu degerler Material 3 varsayilan tipografi olculerini temel alir. Projede
marka dili netlestikce ayni merkezi nesne uzerinden revize edilebilir.

---

## 5. Kullanim Kurallari

- Ekranlarda metin stilleri `MaterialTheme.typography.<slot>` ile kullanilir.
- Yeni bir metin hiyerarsisi gerekiyorsa once mevcut Material 3 slotlari
  degerlendirilir.
- Ayni amac icin farkli ekranlarda farkli `TextStyle` degerleri uretilmez.
- Buton, chip, tab ve benzeri kontrol metinlerinde `label*` slotlari tercih
  edilir.
- Kart ve liste basliklarinda `title*` slotlari tercih edilir.
- Uzun okuma ve aciklama metinlerinde `body*` slotlari tercih edilir.
- Sayfa ve bolum basliklarinda `headline*` slotlari tercih edilir.

---

## 6. Yapilmamasi Gerekenler

Asagidaki kullanimlar tipografi sistemini dagitir ve kullanilmamalidir:

```kotlin
Text(
    text = "Baslik",
    fontSize = 22.sp,
    fontWeight = FontWeight.Bold,
)
```

```kotlin
Text(
    text = "Aciklama",
    style = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
)
```

Dogru kullanim:

```kotlin
Text(
    text = "Baslik",
    style = MaterialTheme.typography.titleLarge,
)
```

---

## 7. Gelecekte Marka Fontu Eklenmesi

Marka fontu eklenecegi zaman karar once bu dokumanda guncellenir.
Ardindan font dosyalari ve `FontFamily` tanimi merkezi olarak `Type.kt`
icinde yapilir.

Olasi gelecek yapi:

```kotlin
private val LyraFontFamily = FontFamily(
    Font(R.font.lyra_regular, FontWeight.Normal),
    Font(R.font.lyra_medium, FontWeight.Medium),
    Font(R.font.lyra_bold, FontWeight.Bold),
)
```

Bu durumda `Typography` icindeki `FontFamily.Default` degerleri
`LyraFontFamily` ile degistirilir. Ekran kodlari `MaterialTheme.typography`
uzerinden stil okudugu icin ekran bazli tipografi guncellemesi yapilmamalidir.
