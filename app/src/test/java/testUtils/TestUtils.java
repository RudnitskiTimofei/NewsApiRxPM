package testUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.model.Source;

public class TestUtils {
    private static final String TIME_STAMP = "01-2020";
    public static final Article TEST_ARTICLE_1 =
            new Article(new Source(null, "SB.BY"), "Timofei", "ConstraintLayout", "New information about ConstraintLayout in Android 10", "http://www.someplace.com", "https://someurlforpictures.com", "18-01-2020");

    public static final Article TEST_ARTICLE_2 =
            new Article(new Source(null, "KP.BY"), "Valeria", "RecyclerView", "New way for using RecyclerView in Android 10", "http://www.somenewplace.com", "https://somenewurlforpictures.com", "19-01-2020");

    public static final List<Article> TEST_ARTICLE_LIST = Collections.unmodifiableList(
            new ArrayList<Article>(){{
                add(TEST_ARTICLE_1);
                add(TEST_ARTICLE_2);
            }}
    );
}
