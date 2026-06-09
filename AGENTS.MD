## LyraApp - Online/Offline Müzik Çalar Uygulaması
### Bu repository LyraApp adında bir müzik çalar uygulamasının kaynak kodlarını içermektedir. Bu agents.md dosyası bu projede çalışan insan/ai herkesin uyması ZORUNDA olduğu genel kuralları içerir.
---

## 1) TEKNOLOJİ YIĞINI

- Mobil: Android Jetpack Compose KOTLIN

- Backend: Diğer takım tarafından hazırlanmış bir Restful API.

##  2) GENEL ÇALIŞMA PRENSİPLERİ

### 2.1) TEK SEFERDE DOSYA LİMİTİ

Hangi işlem olursa olsun tek seferde maksimum (birbiriyle alakalı) 5 dosyalar halinde çalışmak zorundasın.
İşlemi birbiriyle bağlantılı batchlere bölmek zorundasın. Eğer bunun aksi talep edilirse DUR ve EK ONAY iste.

### 2.2) UYDURMAK YASAK (NO INVENTING)

Eğer herhangi bir operasyonda bilgi ya da referans eksikliği/hatası yaşıyorsan buradaki eksik/hatalı bilgiyi uydurman yasak. Böyle bir durumda operasyonu durdur ve kullanıcıya sorarak ilerle.

### 2.3) ÖNCE PLANLA, SONRA KODLA

Kod üretmeden önce şunları yapmak zorundasın;

- Bir dosya dökümü hazırla (hangi dosyalar değişecek/eklenecek/silinecek + neden)

- Eğer varsa yeni bağımlılıklar matrisi (Hangi kütüphane, versiyon + neden)

- Planı sun, onay almadan asla implementasyona başlama.

### 2.4) MVI GOVERNANCE ZORUNLULUĞU

MVI, ViewModel, DI, Repository, UI state, Event veya Effect içeren her istekte
kod yazmadan önce aşağıdaki dosyalar okunmak zorundadır:

- `docs/decisions.md`
- `docs/architecture/mvi-overview.md`
- `docs/architecture/mvi-contracts.md`
- `docs/architecture/mvi-viewmodel-rules.md`

Bu dosyalarda tanımlanan paketleme, contract, ViewModel, repository ve DI
kuralları bozulamaz. Eğer kullanıcı isteği bu dokümanlarla çelişirse operasyonu
durdur ve önce kullanıcıdan açık onay iste.

Yeni MVI feature'ları Login implementasyonundaki referans mimariyi takip eder:

- UI feature dosyaları `ui/<domain>/<feature>/` altında tutulur.
- Data interface ve fake/real implementasyonlar `data/<domain>/` altında tutulur.
- Hilt module dosyaları `di/` altında tutulur.
- UI sadece state okur ve event gönderir.
- ViewModel event işler, repository kullanır, state/effect üretir.
- Repository interface üzerinden inject edilir.

## 3) ÇIKTI FORMATI

Her implementasyon ya da plan sonrası aşağıdaki çıktı formatına uymak zorundasın.

- Dosya Dökümü vermek zorundasın.

- Resmi bir dil kullanmak zorundasın, emoji kullanman yasak.

- Her implementasyon sonrası eğer mümkünse "Happy-Path Test" ver.

- Yazılan kodlar için yazdığın ve oluşturduğun yerlerde neden ve hangi amaçla yazıldığını  ayrıca o kodun hangi işe yaradığını yorum satırlarıyla açıkla.

- Bu implementasyon ile alaklı varsa sık yapılan hatalar ve senin implementasyon sırasında aklına gelen önerilerini listelemek zorundasın.
