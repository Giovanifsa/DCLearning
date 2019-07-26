package ecommerce.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class teste {

	public static void main(String[] args) {
//		Date hoje = new Date(System.currentTimeMillis());
////		String data = java.text.DateFormat.getDateInstance();
//		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//		Date dat = formato.format(hoje);
//		String st = java.text.DateFormat.getDateTimeInstance().format(hoje);
		
//		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		Date data = formato.parse(java.text.DateFormat.getDateInstance());
//		String str = formato.format(data);

		Date hj = new Date(System.currentTimeMillis());
		DateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dado = dt.format(hj);
		
		System.out.println(dado);
	}

}
