/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.analytics.utils;

import java.util.concurrent.TimeUnit;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
@Component
public final class CachedUserAgentStringParser implements UserAgentStringParser {

    private final UserAgentStringParser parser = UADetectorServiceFactory
            .getCachingAndUpdatingParser();
    private final Cache<String, ReadableUserAgent> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(168, TimeUnit.HOURS)
            .build();

    @Override
    public String getDataVersion() {
        return parser.getDataVersion();
    }

    @Override
    public ReadableUserAgent parse(final String userAgentString) {
        ReadableUserAgent result = cache.getIfPresent(userAgentString);
        if (result == null) {
            result = parser.parse(userAgentString);
            cache.put(userAgentString, result);
        }
        return result;
    }

    @Override
    public void shutdown() {
        parser.shutdown();
    }

}
