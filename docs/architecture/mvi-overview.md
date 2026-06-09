### mvi-overview.md

MVI mimarisindeki genel prensipleri, veri akisini ve katman sorumluluklarini
ilgilendiren kurallar burada tanimlanir.

---

## 1) Referans Mimari

LyraApp icinde yeni MVI feature yazilirken Login feature referans alinir.

Referans yapi:

```text
com.turkcell.lyraapp
├── data
│   └── auth
│       ├── AuthRepository.kt
│       └── FakeAuthRepository.kt
├── di
│   └── AuthModule.kt
└── ui
    └── auth
        └── login
            ├── LoginContract.kt
            ├── LoginScreen.kt
            └── LoginViewModel.kt
```

Yeni feature'larda ayni model kullanilir:

```text
data/<domain>/
di/
ui/<domain>/<feature>/
```

---

## 2) Katman Sorumluluklari

### UI

- UI sadece `UiState` okur.
- UI kullanici aksiyonlarini `Event` olarak ViewModel'e gonderir.
- UI dogrudan repository, API, database, token veya session yonetimi cagirmaz.
- UI tek seferlik isleri `Effect` uzerinden disariya acar.

### ViewModel

- ViewModel `Event` isler.
- ViewModel `UiState` gunceller.
- ViewModel tek seferlik isleri `Effect` olarak uretir.
- ViewModel repository interface'lerine bagimli olur.
- ViewModel fake/real implementation sinifini dogrudan bilmez.

### Data

- Repository interface ve fake/real implementasyonlar `data/<domain>/` altinda
  tutulur.
- Fake repository network, token veya gercek session uretmez.
- Gercek repository eklendiginde interface korunur.

### DI

- Hilt module dosyalari `di/` altinda tutulur.
- Repository binding islemleri Hilt module icinde yapilir.
- DI karari `docs/decisions.md` ile uyumlu olmalidir.

---

## 3) Veri Akisi

Standart veri akisi:

```text
User Action -> UI -> Event -> ViewModel -> Repository -> UiState/Effect -> UI
```

Kurallar:

- UI state kaynagi ViewModel'dir.
- Event giris noktasi tek fonksiyon olmalidir: `onEvent(event)`.
- Kalici ekran verisi `UiState` icinde tutulur.
- Snackbar, navigation ve tek seferlik mesajlar `Effect` ile tasinir.

---

## 4) Kapsam Sinirlari

Yeni MVI feature isteginde aksi acikca soylenmedikce:

- Compose Navigation kurulmaz.
- Gercek network yazilmaz.
- Gercek token/session yonetimi yazilmaz.
- Repository once interface uzerinden modellenir.
- Gerekirse fake implementation ile baslanir.
