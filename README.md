# MVVVMMovieApp
 Bu proje, MVVM (Model-View-ViewModel) mimarisi kullanılarak geliştirilmiş bir yemek uygulamasını içermektedir.


Genel Bakış

Bu proje, API kullanarak film verilerini alıp gösteren bir Android uygulamasını içermektedir. Aşağıda, projeyi anlamak ve çalıştırmak için gerekli bilgiler bulunmaktadır.


Özellikler

Home Fragment içinde popüler filmler, yüksek puanlı filmler ve api desteği aldığımız siteye yüklenecek olan filmler recyclerviewlar içinde gösterilmiştir.
Bu recyclerviewlar içinde gösterilen bir filme tıklanarak MovieDetailsActivity'e geçiş yapılabilir ve burada filme ait bilgiler ( poster,isim,puan,süre,çıkış tarihi,türü,yapım şirketi ve yapım şirketinin ülkesi, filme ait bir link ve özet) yer almaktadır.
Burada filme ait api'nin bize sağladığı  sağladığı linke tıklayarak,filme ait bilgilere (bu bilgiler filmden filme değişiyor bazısında tanıtım videoları,bazılarında filmin yapım  şirketinin filme dair linki vb bilgiler sağlanmakta.Burada amaç zaten başarılı bir şekilde web view örneği yapmaktı.) web view üzerinden ulaşabiliriz.
Ayrıca MovieDetailsActivity'de açtığımız filmi sağ üstte bulunan favori butonu ile room database'imize kaydedebiliriz.
Search Fragment'de ise isime göre api üzerinde kayıtlı olan filmleri aratabiliriz.Burada arattığımız kelimeye göre bütün sonuçları recyclerview içinde gösterilir ve elde edilen sonuçların sayısı da üstte belirtilir.Burada da arattığımız sonuçlardan istenilen filme tıklanarak MovieDetailsActivity'e geçiş yapılabilir.
Favorites Fragment'da ise favorilere eklediğimiz filmler recyclerview içinde gösterilmekte.Burada gösterilen film sola kaydırılarak room database'den ve tabi ki recyclerview'dan silinebilir.
Silindikten sonra snackbar içinde filmin ismi ile beraber silindi bilgisi gösterilir.Sncakbar içine yerleştirilen "undo" ya basılarak film tekrardan room database ve recyclerview'a eklenebilir.


Kullanılan Teknolojiler

Kotlin
Android Architecture Components (ViewModel, LiveData)
ViewBinding
NavigationComponent
Retrofit
Glide
Database(room)
Coroutines
RecyclerView
WebView


Ek Bilgiler

Bu uygulama The Movie Database (TMDb) API kullanılarak geliştirilmiştir.


Kurulum

Proje dosyalarını bilgisayarınıza klonlayın veya ZIP olarak indirin.
git clone https://github.com/hariellevardamir/MVVMMovieApp.git
Android Studio'da projeyi açın.
API anahtarınızı Constants.kt dosyasında güncelleyin.
Projeyi çalıştırın.
