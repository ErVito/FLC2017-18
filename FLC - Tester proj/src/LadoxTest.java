public class LadoxTest {
	private Template			   template;
	private static final LadoxTest instance = new LadoxTest();

	private LadoxTest() {
		this.template = new LadoxTemplate(false);
	}

	public static void main(String[] args) {
		LadoxTest.test(1, "sign void foo(int a, String b, char c)");
		LadoxTest.test(2, "sign String alura(Pota n, String pippo)");
		LadoxTest.test(3, "sign noTipo(asd lol)");
		LadoxTest.test(4, "sign int foo(Oups virgola dimenticata)");
		LadoxTest.test(5, "sign int foo(la virgola, questa volta, ci e)");
		LadoxTest.test(6,  "sign tipo senzaParentesi par uno, par due)");
		LadoxTest.test(7, "sign tipo foobar()");
	}
	public static void test(int i, String input) {
		String translation;

		System.out.println(" === Test "+i+" ===");
		translation = LadoxTest.instance.template.translate(input);
		System.out.println(translation);
	}
}