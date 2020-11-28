package dsdbahw2.compute;

import java.util.concurrent.TimeUnit;

public class Scale {

    private long scale;

    public Scale(String rawScale) {
        if (rawScale.endsWith("ms")) {
            scale = Integer.parseInt(rawScale.replace("ms", ""));
        } else if (rawScale.endsWith("s")) {
            scale = TimeUnit.SECONDS.toMillis(Integer.parseInt(rawScale.replace("s", "")));
        } else if (rawScale.endsWith("m")) {
            scale = TimeUnit.MINUTES.toMillis(Integer.parseInt(rawScale.replace("m", "")));
        } else if (rawScale.endsWith("h")) {
            scale = TimeUnit.HOURS.toMillis(Integer.parseInt(rawScale.replace("h", "")));
        } else if (rawScale.endsWith("d")) {
            scale = TimeUnit.DAYS.toMillis(Integer.parseInt(rawScale.replace("d", "")));
        }
    }

    public long getNewTime(long timestamp) {
        long mod = timestamp % scale;
        long result = timestamp - mod;
        return result;
    }
}
