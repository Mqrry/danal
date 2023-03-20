package com.ksk.danal;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class URLEncoded {
    private final Map<String, String> pair = new LinkedHashMap<>();

    @SafeVarargs
    public URLEncoded(Pair<String, String>... pairs) {
        for (Pair<String, String> p : pairs)
            pair.put(p.getFirst(), p.getSecond());
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : pair.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8)).append('&');
        }
        return sb.deleteCharAt(sb.lastIndexOf("&")).toString();
    }
}
