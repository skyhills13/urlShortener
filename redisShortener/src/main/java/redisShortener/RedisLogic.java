package redisShortener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

import redis.clients.jedis.Jedis;


public class RedisLogic {

	private Jedis jedis = new Jedis("10.73.45.60");
	private final String LONG_TO_SHORT = "LONGTOSHORT";
	private final String SHORT_TO_LONG = "SHORTTOLONG";
	
	
	public String makeShort(String longUrl) {
		return (String)UUID.randomUUID().toString().subSequence(0, 8);
	}

	public String getShort(String serverName, int port, String contextPath,
			String longUrl) throws Exception {
		
		File redisCommands = new File("./redisCommands");
		
		BufferedWriter out = new BufferedWriter(new FileWriter(redisCommands, true));
		
		String shortUrl = jedis.hget(LONG_TO_SHORT, longUrl);
		
		System.out.println(shortUrl);
		if (shortUrl != null) {
			// if id is not null, this link has been shorten already.
			// nothing to do
			//shortUrl = jedis.get(longUrl);
		} else {
			shortUrl = makeShort(longUrl);
			// at this point id is null, make it shorter
			jedis.hset(LONG_TO_SHORT, longUrl, shortUrl);
			jedis.hset(SHORT_TO_LONG, shortUrl, longUrl);
//			jedis.set(longUrl, makeShort(longUrl));
					
			
			out.write((new StringBuffer()).append(longUrl).append(",").append(shortUrl).append("\n").toString());
	
			// after we insert the record, we obtain the ID as identifier of our
			// new short link
			System.out.println(shortUrl + ", " + jedis.hget(LONG_TO_SHORT, longUrl));
			System.out.println(jedis.hget(SHORT_TO_LONG, shortUrl));
		}
		out.flush();
		out.close();
		return "http://" + serverName + ":" + port + contextPath + "/" + shortUrl;
	}

	public String getLongUrl(String urlShort) throws Exception {
		if (urlShort.startsWith("/")) {
			urlShort = urlShort.replace("/", "");
		}
		
		String longUrl = jedis.hget(SHORT_TO_LONG, urlShort);
		
		return longUrl;
	}

}