package OnlineMarketplaceSystem;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class MarketplaceTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		Marketplace m = new Marketplace();
		
		Scanner unitScanner = new Scanner("Siyi Xian\n123456\n");
		assertEquals(true, m.login(1));
	}

}
