package by.it.andersen.newsapirxpm.repository;

import android.content.Context;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import by.it.andersen.newsapirxpm.datasource.ArticleDao;
import by.it.andersen.newsapirxpm.datasource.NewsDatabase;
import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.model.Source;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import testUtils.TestUtils;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NewsRepositoryTest {
    private List<Article> articleList;
    Article article;

    private List<Article> testList;
    NewsDatabase database;
    ArticleDao articleDao;
    NewsRepository repository;

    @Rule
    public ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            Context context = ApplicationProvider.getApplicationContext();
            database = Room.inMemoryDatabaseBuilder(context, NewsDatabase.class).build();
            articleDao = database.article();
            repository = NewsRepository.getInstance(context);
            setUpScheduler();
            initData();
        }

        @Override
        protected void after() {
            database.close();
        }
    };


    @Test
    public void getData() {
        List<Article> list = repository.getData("software");
        assertEquals(testList.get(0).getSource().getId(), list.get(0).getSource().getId() );

    }

    @Test
    public void getDataFromDatabase() {
        article = TestUtils.TEST_ARTICLE_1;
        new Thread(() -> {
            articleDao.inserArticle(article);
            articleList = articleDao.getAllTest();
            assertEquals(article.getDescription(), articleList.get(0).getDescription());
        });

    }

    @Test
    public void testingGetAllNews(){
        assertNotNull(repository.getAllNews("software"));
    }

    public void setUpScheduler() {

        Scheduler immediate = new Scheduler() {
            @Override
            public Scheduler.Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    public void initData() {
        testList = new ArrayList<>();
        testList.add(new Article(new Source(null, "20minutos.es"), "Manu Contreras", "El antivirus de Windows quiere conquistar Android y iOS", "Microsoft Defender, el antivirus que viene integrado en Windows 10 por defecto llegará a móviles Android y iOS a finales de año, según ha confirmado la propia Microsoft en un artículo en su blog de seguridad. El antivirus de Microsoft es hoy en día uno de los…", "https://clipset.20minutos.es/el-antivirus-de-windows-quiere-conquistar-android-y-ios/", "https://i0.wp.com/clipset.20minutos.es/wp-content/uploads/2020/02/windows-defender-android-ios.jpg?fit=1600%2C1048&ssl=1", "2020-02-21T09:49:12Z"));
        testList.add(new Article(new Source(null, "Tweaktown.com"), "TheInsertus", "AOC take a leap into peripheral gaming market with keyboards and mice", "AOC has revealed that they will be jumping into the peripheral market with new gaming keyboards and mice", "https://www.tweaktown.com/news/70785/aoc-take-leap-into-peripheral-gaming-market-with-keyboards-and-mice/index.html", "https://images.tweaktown.com/news/7/0/70785_08_aoc-set-to-enter-the-peripheral-market-with-new-keyboards-and-mice_full.jpg", "2020-02-21T09:48:02Z"));
        testList.add(new Article(new Source(null, "Epmmagazine.com"), "James Steiner", "Thinking smart: How to be successful with IoT in pharma - EPM Magazine", "James Steiner, design director at Method explains how pharma companies can ensure they’re successful when implementing an IoT project.", "https://www.epmmagazine.com/opinion/thinking-smart/", "https://www.epmmagazine.com/downloads/7379/download/Smart%20thinking%20.jpg?cb=6fbfc5b8ca96875d0318b300990eea12&w=1200", "2020-02-21T09:47:53Z"));
        testList.add(new Article(new Source(null, "4gamer.net"), "4Gamer編集部", "Yahoo!ゲーム かんたんゲームに「2048レジェンド」が追加", "かんたんゲーム Yahoo!ゲーム配信元ヤフー配信日2020/02/21＜以下，メーカー発表文の内容をそのまま掲載しています＞「Yahoo!ゲーム かんたんゲーム」にて数字をどんどん大きくしていこう。定番の数字パズル『2048レジェンド』を配信開始    株式会社ワーカービー（東京都千代田区、代表取締役：加藤祐司）は、2020年2月21日よりブラウザで簡単に遊べるゲーム『2048レジェンド』をヤフ…", "https://www.4gamer.net/games/316/G031605/20200221128/", "https://www.4gamer.net/games/316/G031605/20200221128/TN/006.jpg", "2020-02-21T09:46:03Z"));
        testList.add(new Article(new Source(null, "Cybersecurity-help.cz"), null, "Ubuntu update for ppp", "Security Advisory. This security advisory describes one high risk vulnerability. 1) Buffer overflow. Severity: High. CVSSv3: [PCI] CVE-ID: CVE-2020-8597. CWE-ID: Description. CWE-119 - Improper Restriction of Operations within the Bounds of a Memory Buffer Th…", "https://www.cybersecurity-help.cz/vdb/SB2020022109", null, "2020-02-21T09:43:00Z"));
        testList.add(new Article(new Source(null, "Tuttoandroid.net"), "Vincenzo", "Samsung Galaxy A70 riceve finalmente l’aggiornamento ad Android 10 con One UI 2.0", "Samsung Galaxy A70 sta finalmente iniziando a ricevere l'agognato aggiornamento software ad Android 10 con annessa One UI 2.0 di Samsung. L'articolo Samsung Galaxy A70 riceve finalmente l’aggiornamento ad Android 10 con One UI 2.0 proviene da TuttoAndroid.", "https://www.tuttoandroid.net/aggiornamenti/samsung-galaxy-a70-android-10-one-ui-2-roll-out-783169/", "https://img.tuttoandroid.net/wp-content/uploads/2019/07/samsung_galaxy_a70_8_tta.jpg", "2020-02-21T09:41:05Z"));
        testList.add(new Article(new Source(null, "Forbes.com"), "Adrian Bridgwater, Contributor, Adrian Bridgwater, Contributor https://www.forbes.com/sites/adrianbridgwater/", "How Israel Became A Technology Startup Nation", "Why should Israel have shown its mettle in the tech market? What drove this region to become a software-centric startup nation of young companies vying for a place on the global tech map?", "https://www.forbes.com/sites/adrianbridgwater/2020/02/21/how-israel-became-a-technology-startup-nation/", "https://thumbor.forbes.com/thumbor/600x315/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5e4f93217a0098000733e260%2F960x0.jpg", "2020-02-21T09:38:04Z"));
        testList.add(new Article(new Source(null, "Publicissapient.fr"), "Clément Rochas", "Cinq effets inattendus de la pratique du BDD", "Le Behaviour-Driven Development (BDD) est une méthode éprouvée. Apparue aux alentours de 2005 cette méthode de développement propose de se baser sur des exemples. Métiers, testeurs et développeurs se mettent autour de la table afin se parler et surtout de se …", "https://blog.engineering.publicissapient.fr/2020/02/21/cinq-effets-inattendus-de-la-pratique-du-bdd/", "https://blog.engineering.publicissapient.fr/wp-content/uploads/2020/02/3-amigos.jpg", "2020-02-21T09:37:57Z"));
        testList.add(new Article(new Source(null, "Creativereview.co.uk"), "Emma Tucker", "Toss a coin to The Witcher VFX artists", "As rumours swirl around season two of The Witcher, CR delves into how the fantasy world of this much-loved guilty pleasure was created The post Toss a coin to The Witcher VFX artists appeared first on Creative Review.", "https://www.creativereview.co.uk/creatives-world-witcher/", "https://creativereview.imgix.net/content/uploads/2020/02/witcher.jpg", "2020-02-21T09:37:31Z"));
        testList.add(new Article(new Source(null, "Gamesindustry.biz"), null, "COVID-19 looms over planning for next-gen launches", "The epidemic threatens to shut down supply chains the entire games industry relies on -- and even push new console launches into next year", "https://www.gamesindustry.biz/articles/2020-02-21-covid-19-looms-over-planning-for-next-gen-launches", "https://images.eurogamer.net/2020/articles/2020-02-21-09-27/xbox_series_x_1.jpg", "2020-02-21T09:36:00Z"));
        testList.add(new Article(new Source(null, "Tutsplus.com"), "Marie Gardiner", "3 Top Eco-friendly Travel Video Templates for After Effects", "Travel seems\r\ncheaper and accessible to more people than ever before. However, many people ask: can we still do the things we want to do, travel where we want to go, but be more\r\nenvironmentally...", "https://photography.tutsplus.com/articles/3-top-video-templates-for-business-eco-friendly-travel--cms-34675", "https://cms-assets.tutsplus.com/uploads/users/392/posts/34675/preview_image/eco-travel-after-effects.jpg", "2020-02-21T09:36:00Z"));
        testList.add(new Article(new Source(null, "Computable.nl"), null, "Ericsson Nederland viert honderdste verjaardag", "De Nederlandse vestiging van het Zweedse Ericsson aan de Ericssonstraat in Rijen viert dit jaar haar honderdste verjaardag.", "https://www.computable.nl/artikel/nieuws/ict-branche/6883105/250449/ericsson-nederland-viert-honderdste-verjaardag.html", "https://www.computable.nl/img/56/66/org_org/5666270.jpg", "2020-02-21T09:34:00Z"));
        testList.add(new Article(new Source(null, "Tomshw.it"), "Gianluca Saitto", "Final Fantasy 11: la versione per dispositivi mobile è ancora in sviluppo", "Stando alla software house Nexom, la versione per dispositivi mobile dell'MMORPG Final Fantasy 11 è attualmente ancora in sviluppo.", "https://www.tomshw.it/videogioco/final-fantasy-11-mobile-ancora-in-sviluppo/", "https://www.tomshw.it/images/images/2020/02/final-fantasy-xi-77939.768x432.jpg", "2020-02-21T09:31:05Z"));
        testList.add(new Article(new Source(null, "Freerepublic.com"), "The Gatestone Institute", "UK Court: Sharia Marriages Not Valid Under English Law", "The Court of Appeal, the second-highest court in England and Wales after the Supreme Court, has ruled that the Islamic marriage contract, known as nikah in Arabic, is not valid under English law. The landmark ruling has far-reaching implications. On the one h…", "https://www.freerepublic.com/focus/f-news/3818251/posts", null, "2020-02-21T09:30:49Z"));
        testList.add(new Article(new Source(null, "Version2.dk"), "Frederik Marcher Hansen", "Computergeniet bag kopier, klip og sæt ind: Larry Tesler er død", "Takket være Larry Tesler er din arbejdsdag nemmere.", "https://www.version2.dk/artikel/computergeniet-bag-kopier-klip-saet-ind-larry-tesler-doed-1090077", "https://www.version2.dk/sites/v2/files/styles/large/public/topillustration/2020/02/larryteslerandwhisper6.jpeg?itok=xmNouvSs", "2020-02-21T09:30:03Z"));
        testList.add(new Article(new Source(null, "Ain.ua"), "Новости компаний", "EPAM запрошує на другу хвилю магістерської програми «Інженерія програмного забезпечення»", "Минулого року світовий постачальник ІТ-послуг ЕРАМ та його партнер Національний університет «Києво-Могилянська академія» запустили спільну магістерську програму «Інженерія програмного забезпечення». Мета такої співпраці — якісно нова підготовка досві...", "https://ain.ua/2020/02/21/epam-zaproshuye-na-drugu-xvilyu-magistersko%D1%97-programi-inzheneriya-programnogo-zabezpechennya/", "https://ain.ua/wp-content/uploads/2020/02/PR-22-600x315.png", "2020-02-21T09:30:00Z"));
    }
}