package it.unibs.fp.mylib;
import java.io.*;


public class FileService
{
	private final static String MSG_NO_FILE = "ATTENZIONE: NON TROVO IL FILE ";
	private final static String MSG_NO_READ = "ATTENZIONE: PROBLEMI CON LA LETTURA DEL FILE ";
	private final static String MSG_NO_WRITE = "ATTENZIONE: PROBLEMI CON LA SCRITTURA DEL FILE ";
	private final static String MSG_NO_CLOSE ="ATTENZIONE: PROBLEMI CON LA CHIUSURA DEL FILE ";
  	
	public static Object loadSingleObject (File f)
	 {
		 Object read = null;
		 ObjectInputStream input = null;
			
		 try
			{
			 input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
				
			 read = input.readObject();
				
			}
		 catch (FileNotFoundException excNotFound)
			{
			 System.out.println(MSG_NO_FILE + f.getName() );
			}
		 catch (IOException excLettura)
			{
			 System.out.println(MSG_NO_READ + f.getName() );
			}
		 catch (ClassNotFoundException excLettura)
			{
			 System.out.println(MSG_NO_READ + f.getName() );
			}
  	 finally
			{
			 if (input != null)
				{
				 try 
					{
				   input.close();
					}
				 catch (IOException excChiusura)
					{
			 			System.out.println(MSG_NO_CLOSE + f.getName() );
					}
				}
			} // finally

		 return read;
		  
	 } // metodo caricaSingoloOggetto
	
	
	public static void saveSingleObject (File f, Object daSalvare)
	 {
		 ObjectOutputStream output = null;
			
		 try
			{
			 output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
				
			 output.writeObject(daSalvare);
				
			}
		 catch (IOException excScrittura)
			{
			 System.out.println(MSG_NO_WRITE + f.getName() );
			}
		 
  	     finally
			{
			 if (output != null)
				{
				 try 
				  {
				   output.close();
				  }
				 catch (IOException excChiusura)
					{
			 			System.out.println(MSG_NO_CLOSE + f.getName() );
					}
				}
			} // finally

		 } // metodo salvaSingoloOggetto
	
	
}

