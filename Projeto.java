import java.util.Scanner;
import java.io.*;
import java.util.Random;
public class Projeto{
   public static void main(String[] args) {
      Scanner read = new Scanner(System.in);
      boolean sair = false;
      while(!sair){
        try {

            System.out.println("\n------------* MENU *------------\n0 - Escrever em um arquivo\n1 - Criptografar um arquivo\n2 - Descriptografar um arquivo\n3 - Sair do programa");
            System.out.print("\nOpcao desejada: ");
            int opcao = read.nextInt();

            switch(opcao){
               case 0:
                  escreverArquivo();
                  break;
               case 1:
                  criptografarArquivo();
                  break;
               case 2:
                  descriptografarArquivo();
                  break;
               case 3:
                  sair = true;
                  break;
               default:
                  System.out.println("Opcao invalida");
                  break;
            }

         }catch(Exception e) {
             System.out.print("Erro: ");
            e.printStackTrace();
         }
      }
      read.close();
    } // fim do main()

    public static void escreverArquivo()throws Exception{
       Scanner read = new Scanner(System.in);
       String nome = "";
       System.out.print("Qual o nome do arquivo? ");
       nome = read.nextLine();
       RandomAccessFile raf = new RandomAccessFile(nome, "rw");
       System.out.println("Digite o que deseja escrever no arquivo:");
       String text = read.nextLine();
       byte[] b = text.getBytes();
       raf.read(b);
       Random random = new Random();
       int chave = random.nextInt(100);
       System.out.println("Sua chave de criptografia eh: " + chave);
       b = cifrar(b,chave);
       raf.seek(0);
       raf.writeInt(chave);
       raf.write(b);
       raf.close();
       read.close();
    }

    public static void criptografarArquivo() throws Exception{
       Scanner read = new Scanner(System.in);
       System.out.print("Digite o nome do arquivo: ");
       String name = read.nextLine();
       RandomAccessFile raf = new RandomAccessFile(name, "rw");
       int tam = (int)raf.length();
       byte[] b = new byte[tam];
       raf.read(b);
       Random random = new Random();
       int chave = random.nextInt(100);
       System.out.println("Sua chave de criptografia eh: " + chave);
       b = cifrar(b, chave);
       raf.seek(0);
       raf.writeInt(chave);
       raf.write(b);
       raf.close();
       read.close();
    }

    public static void descriptografarArquivo() throws Exception{
       Scanner read = new Scanner(System.in);
       System.out.print("Digite o nome do arquivo: ");
       String name = read.nextLine();
       RandomAccessFile raf = new RandomAccessFile(name, "rw");
       int chave_correta = raf.readInt();
       boolean chave_errada = false;
       int chave = 0;
       do{
         chave_errada = false;
         System.out.print("Qual eh a sua chave de criptografia? ");
         chave = read.nextInt();
         if(chave != chave_correta){
            chave_errada = true;
            System.out.println("Chave incompativel");
         }
       }while(chave_errada);
       int tam = (int)raf.length()-4;
       byte[] b = new byte[tam];

       raf.read(b);
       b = decifrar(b, chave);
       raf.close();
       File f = new File(name);
       f.delete();
       raf = new RandomAccessFile(name, "rw");
       raf.write(b);
       raf.close();
       read.close();
    }
    
    public static byte[] cifrar(byte[] b, int chave){
      for(int i = 0; i < b.length; i++){
         b[i] = (byte)(b[i]+(byte)chave);   
      }
    return b;
    }
    
    public static byte[] decifrar(byte[] b, int chave){
      for(int i = 0; i < b.length; i++){
         b[i] = (byte)(b[i]-(byte)chave);   
      }
     return b;
    }
}