package tests;

import org.junit.BeforeClass;
import org.junit.Test;

import main.DeductiveDB;

public class TestDeductiveDB {

	static DeductiveDB deductiveDB;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		deductiveDB = new DeductiveDB();
	}

	@Test
	public void prologFilesShouldQueryFromJava() {
		try {
			deductiveDB.consultKB();

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
