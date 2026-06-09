### mvi-contracts.md

MVI contract dosyalarinin nasil yazilacagini tanimlar.

---

## 1) Dosya Adi ve Konumu

Her MVI feature icin contract dosyasi feature UI paketi altinda tutulur:

```text
ui/<domain>/<feature>/<Feature>Contract.kt
```

Ornek:

```text
ui/auth/login/LoginContract.kt
```

---

## 2) Contract Icerigi

Her contract dosyasinda uc temel yapi bulunur:

```kotlin
data class FeatureUiState(...)

sealed interface FeatureEvent { ... }

sealed interface FeatureEffect { ... }
```

Bu yapilar ayni dosyada tutulur. Yeni feature icin ekstra contract dosyalari
uretilmez.

---

## 3) UiState Kurallari

- `UiState` immutable `data class` olmalidir.
- Varsayilan degerler constructor icinde verilmelidir.
- UI'da kalici olarak gorunen veya kullanilan ekran verileri burada tutulur.
- Loading bilgisi gerekiyorsa `isLoading: Boolean = false` kullanilir.
- Password visibility gibi UI durumu gerekiyorsa state icinde tutulur.
- Repository veya domain model dogrudan UI ihtiyaci degilse state'e eklenmez.

Referans:

```kotlin
data class LoginUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
)
```

---

## 4) Event Kurallari

- `Event` sealed interface olmalidir.
- Kullanici aksiyonlari event olarak modellenir.
- Input degisimleri `data class` event olur.
- Parametresiz aksiyonlar `data object` event olur.
- UI icinden ViewModel'e sadece event gonderilir.

Referans:

```kotlin
sealed interface LoginEvent {
    data class PhoneChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data object PasswordVisibilityClicked : LoginEvent
    data object LoginClicked : LoginEvent
    data object ForgotPasswordClicked : LoginEvent
    data object RegisterClicked : LoginEvent
}
```

---

## 5) Effect Kurallari

- `Effect` sealed interface olmalidir.
- Navigation, snackbar, toast, dialog ve tek seferlik mesajlar effect olarak
  modellenir.
- Kalici ekran verisi effect icinde tutulmaz.
- Effect isimleri sonuc veya yonlendirme niyetini acik anlatmalidir.

Referans:

```kotlin
sealed interface LoginEffect {
    data object NavigateForgotPassword : LoginEffect
    data object NavigateRegister : LoginEffect
    data object ShowLoginSuccess : LoginEffect
    data object ShowLoginError : LoginEffect
}
```
