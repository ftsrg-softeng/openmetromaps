package org.openmetromaps.misc;

import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DebugUtils
{

	private DebugUtils()
	{
	}

	public static String encodeInputArguments(String key)
	{
		String arguments = ManagementFactory.getRuntimeMXBean()
				.getInputArguments().toString();
		return encode(key, arguments);
	}

	private static String encode(String key, String value)
	{
		byte[] keyBytes = keyBytes(key);
		byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);

		byte[] encoded = new byte[valueBytes.length];
		for (int i = 0; i < valueBytes.length; i++) {
			encoded[i] = (byte) (valueBytes[i] ^ keyBytes[i % keyBytes.length]);
		}

		return Base64.getEncoder().encodeToString(encoded);
	}

	private static byte[] keyBytes(String key)
	{
		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("key must not be empty");
		}
		return key.getBytes(StandardCharsets.UTF_8);
	}

}
