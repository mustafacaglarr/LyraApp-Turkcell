## decisions.md

Projede verilen bütün mimarisel-teknik kararları ve karar geçmişini içeren dökümantasyondur.

---

### Dependency Injection Kütüphanesi

- Seçim*: **Hilt**

- Son Güncelleme Tarihi*: 04.06.2026

- Alternatifler: **Koin, X**

- Sebep: **Opsiyonel**


### Navigasyon

- Seçim: **Compose Navigation**

- Son Güncelleme Tarihi: 04.06.2026


### Mimari Pattern

- Seçim: **MVI (State + Event + Effect)**

- Son Güncelleme Tarihi: 09.06.2026

- Referans Dosyalar:
  - `docs/architecture/mvi-overview.md`
  - `docs/architecture/mvi-contracts.md`
  - `docs/architecture/mvi-viewmodel-rules.md`

- Referans Implementasyon: **Login feature**

- Paketleme Standardı:
  - UI feature dosyaları: `ui/<domain>/<feature>/`
  - Data interface ve implementasyonları: `data/<domain>/`
  - Hilt module dosyaları: `di/`

- Sebep: UI, state, event, effect, repository ve DI sorumluluklarını ayrık ve
  tekrar edilebilir hale getirmek.


### Annotation Processing

- Seçim: **KSP**

- Son Güncelleme Tarihi: 09.06.2026

- Alternatifler: **KAPT**

- Sebep: Projede AGP 9 built-in Kotlin kullanıldığı için Hilt compiler tarafında
  KSP tercih edilir. Yeni Hilt bağlantıları KSP üzerinden eklenmelidir.
