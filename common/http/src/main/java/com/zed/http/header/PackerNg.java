package com.zed.http.header;

import android.content.Context;

import com.zed.common.util.LogUtils;
import com.meituan.android.walle.WalleChannelReader;

/**
 * Desc  : 打包工具
 *
 * https://github.com/mcxiaoke/packer-ng-plugin
 */
public class PackerNg {

    private static final String TAG = PackerNg.class.getSimpleName();

    private static final String EMPTY_STRING = "";

    private static String sCachedMarket;

    public static String getMarket(final Context context) {
        return getMarket(context, EMPTY_STRING);
    }

    public static synchronized String getMarket(final Context context, final String defaultValue) {
        if (sCachedMarket == null) {
            sCachedMarket = getMarketInternal(context, defaultValue).market;
        }
        return sCachedMarket;
    }

    public static MarketInfo getMarketInfo(final Context context) {
        return getMarketInfo(context, EMPTY_STRING);
    }

    public static synchronized MarketInfo getMarketInfo(final Context context,
        final String defaultValue) {
        return getMarketInternal(context, defaultValue);
    }

    private static MarketInfo getMarketInternal(final Context context, final String defaultValue) {
        String market;
        Exception error;
        try {
            market = WalleChannelReader.getChannel(context.getApplicationContext());
            LogUtils.d(TAG,"WalleChannelReader market---->"+market);
            error = null;
        } catch (Exception e) {
            market = null;
            error = e;
            LogUtils.e(e);
        }
        return new MarketInfo(market == null ? defaultValue :market, error);
    }

    public static final class MarketInfo {

        public final String market;

        public final Exception error;

        public MarketInfo(final String market, final Exception error) {
            this.market = market;
            this.error = error;
        }

        @Override
        public String toString() {
            return "MarketInfo{" + "market='" + market + '\'' + ", error=" + error + '}';
        }
    }


}
