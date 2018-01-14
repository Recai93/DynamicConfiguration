import jar.ConfigurationReader;

/**
 * Created by rajaee on 1/13/18.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ConfigurationReader configurationReader = new ConfigurationReader("SERVICE-A",
                "mongodb://admin:admin@ds247357.mlab.com:47357/confdb",
                10000);
        String siteName = configurationReader.<String>GetValue("SiteName");
        Integer maxItemCount = configurationReader.<Integer>GetValue("MaxItemCount");
        Boolean isBasketEnabled = configurationReader.<Boolean>GetValue("IsBasketEnabled");
        System.out.println("siteName = " + siteName);
        System.out.println("maxItemCount = " + maxItemCount);
        System.out.println("isBasketEnabled = " + isBasketEnabled);
    }
}
