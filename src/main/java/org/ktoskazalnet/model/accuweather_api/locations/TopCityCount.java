package org.ktoskazalnet.model.accuweather_api.locations;

public enum TopCityCount {
    FIFTY(50), HUNDRED(100), ONE_HUNDRED_FIFTY(150);

    private final int count;

    TopCityCount(int count) {
        this.count = count;
    }

    public static TopCityCount valueOf(int num) {
        if (num == 150) {
            return TopCityCount.ONE_HUNDRED_FIFTY;
        } else if (num == 100) {
            return TopCityCount.HUNDRED;
        }
        return TopCityCount.FIFTY;
    }

    public int getCount() {
        return count;
    }
}
