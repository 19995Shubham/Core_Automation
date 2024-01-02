package common.core.Utility;

import java.net.*;

public class BrokenLinks {
    public static void checkBrokenLink(String linkUrl) {

        try {
            URL url = new URL(linkUrl);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(5000);
            httpUrlConnection.connect();

            if (httpUrlConnection.getResponseCode() >= 400) {
                System.out.println("is broken " + linkUrl + "------>" + httpUrlConnection.getResponseMessage() + " is broken");
                //	log.error(linkUrl, httpUrlConnection.getResponseMessage(),httpUrlConnection.getResponseCode());

            } else {
                System.out.println(linkUrl + "-->" + httpUrlConnection.getResponseMessage());
                //	log.error(linkUrl, httpUrlConnection.getResponseMessage(),httpUrlConnection.getResponseCode());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}