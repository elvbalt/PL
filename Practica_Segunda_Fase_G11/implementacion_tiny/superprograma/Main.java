package superprograma;

import cc.asint.CCMain;
import cup.asint.CupMain;

public class Main {

	public static void main(String[] args) throws Exception {
		switch(args[1]) {
		case "asc":
			CupMain.main(new String[]{args[0]});
			break;
		case "desc":
			CCMain.main(new String[]{args[0]});
			break;
		}
	}

}
