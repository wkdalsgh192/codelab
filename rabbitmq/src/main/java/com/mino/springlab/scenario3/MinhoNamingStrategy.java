package com.mino.springlab.scenario3;

import org.springframework.amqp.core.NamingStrategy;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class MinhoNamingStrategy implements NamingStrategy {

    private static final int SIXTEEN = 16;

    /**
     * The default instance - using {@code spring.gen-} as the prefix.
     */
    public static final org.springframework.amqp.core.Base64UrlNamingStrategy DEFAULT = new org.springframework.amqp.core.Base64UrlNamingStrategy();

    private final String prefix;

    /**
     * Construct an instance using the default prefix {@code spring.gen-}.
     */
//    public MinhoNamingStrategy() {
//        this(prefix);
//    }

    /**
     * Construct an instance using the supplied prefix.
     * @param prefix The prefix.
     */
    public MinhoNamingStrategy(String prefix) {
        Assert.notNull(prefix, "'prefix' cannot be null; use an empty String ");
        this.prefix = prefix;
    }

    @Override
    public String generateName() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[SIXTEEN]);
        bb.putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits());
        // Convert to base64 and remove trailing =
        return this.prefix + Base64Utils.encodeToUrlSafeString(bb.array())
                .replaceAll("=", "");
    }

}
