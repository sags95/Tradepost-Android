package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by HenryChiang on 15-05-25.
 */
public class MarketPlaceData {
    public String itemImage;
    public String proUsername;
    public String itemTitle;

    public MarketPlaceData() {
    }


    public static ArrayList<MarketPlaceData> generateSampleData() {
        String repeat = " repeat";
        final ArrayList<MarketPlaceData> datas = new ArrayList<MarketPlaceData>();
        for (int i = 0; i < 10; i++) {
            MarketPlaceData data = new MarketPlaceData();
            data.itemImage = "https://jiresal-test.s3.amazonaws.com/deal3.png";
            data.proUsername = "User";
            data.itemTitle = "Awesome title";
            Random ran = new Random();
            int x = ran.nextInt(i + 10);
            for (int j = 0; j < x; j++)
                data.itemTitle += repeat;
            datas.add(data);
        }
        return datas;

    }
}
