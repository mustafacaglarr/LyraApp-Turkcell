### mvi-viewmodel-rules.md

MVI ViewModel dosyalarinin nasil yazilacagini tanimlar.

---

## 1) Dosya Adi ve Konumu

Her MVI ViewModel feature UI paketi altinda tutulur:

```text
ui/<domain>/<feature>/<Feature>ViewModel.kt
```

Ornek:

```text
ui/auth/login/LoginViewModel.kt
```

---

## 2) Hilt Kullanimi

- ViewModel `@HiltViewModel` ile isaretlenir.
- Constructor injection kullanilir.
- Repository interface uzerinden inject edilir.
- ViewModel fake veya real repository implementasyonunu dogrudan bilmez.

Referans:

```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel()
```

---

## 3) StateFlow Kurallari

- Mutable state private tutulur.
- Public state `StateFlow` olarak acilir.
- UI sadece public state'i collect eder.
- State guncellemek icin `update { ... }` tercih edilir.

Referans:

```kotlin
private val _uiState = MutableStateFlow(LoginUiState())
val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
```

---

## 4) SharedFlow Effect Kurallari

- Mutable effect flow private tutulur.
- Public effect `SharedFlow` olarak acilir.
- Tek seferlik effect icin `MutableSharedFlow` kullanilir.
- Effect uretmek icin `emit` veya gerekli yerde `tryEmit` kullanilir.

Referans:

```kotlin
private val _effect = MutableSharedFlow<LoginEffect>(extraBufferCapacity = 1)
val effect: SharedFlow<LoginEffect> = _effect.asSharedFlow()
```

---

## 5) Event Giris Kurali

ViewModel disariya tek event giris fonksiyonu acar:

```kotlin
fun onEvent(event: FeatureEvent)
```

Kurallar:

- UI ViewModel metodlarini tek tek cagirmamalidir.
- UI sadece `onEvent(...)` uzerinden aksiyon bildirir.
- Event handling `when (event)` ile merkezi yapilir.

---

## 6) Coroutine Kurallari

- Suspend repository cagrilari `viewModelScope.launch` icinde yapilir.
- Loading state is baslamadan once `true`, is bittikten sonra `false` yapilir.
- Hata ve sonuc durumlari effect veya state ile acikca temsil edilir.

---

## 7) Yasaklar

ViewModel icinde asagidakiler yazilmaz:

- Retrofit veya OkHttp kurulumu.
- Gercek token/session yonetimi.
- Android `Context` bagimli is mantigi.
- UI composable kodu.
- Fake implementation tipine dogrudan bagimlilik.

Bu ihtiyaclar ortaya cikarsa repository, use-case veya DI katmani uzerinden
ayri planlanir.
