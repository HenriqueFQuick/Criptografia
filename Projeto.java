import java.util.Scanner;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.InputMismatchException;

/**

 * @author Luiz Junio
 * @author Allan Vivekanda
 * @author Breno Soares
 * @author Henrique Fernandes
 * */

public class Projeto
{//Inicio classe Projeto

    private static final Scanner read = new Scanner(System.in);

    public static void main(String[] args) {
        boolean sair = false;
        while(!sair){
            try {
                System.out.println(
                        "\n------------* MENU *------------\n" + 
                        "0 - Escrever em um arquivo e criptografar\n" +
                        "1 - Criptografar um arquivo\n" +
                        "2 - Descriptografar um arquivo\n" +
                        "3 - Sair do programa"
                        );
                System.out.print("\nOpcao desejada: ");
                byte opcao = read.nextByte();
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
            }
            catch(InputMismatchException inputMismatchException){
                System.out.println("\nOops! Parece que digitou algo errado por engano!\nTente novamente!");
                read.next(); // Limpa buffer do scanner
            }
            catch(FileNotFoundException fileNotFoundException){
                System.out.println("\nOops! Não existe arquivo com esse nome!\nTente novamente!");
                read.next(); // Limpa buffer do scanner 
            }
            catch(Exception exception) {
                System.out.print("Erro: ");
                exception.printStackTrace();
                sair = true;
            }
        }
        read.close();
    } // fim do main()

    /*metodo para criar um novo arquivo e escrever a mensagem desejada ja criptografada 
    ( a chave de criptografia sera escrita no arquivo para poder validar a mesma na descriptagrafia)*/ 
    public static void escreverArquivo() throws Exception{
        String nome = "";
        System.out.print("Qual o nome do arquivo? ");
        nome = read.nextLine();
        nome = read.nextLine();
        RandomAccessFile raf = new RandomAccessFile(nome, "rw");
        System.out.println("Digite o que deseja escrever no arquivo:\n");
        String text = read.nextLine();
        byte[] b = text.getBytes();
        raf.read(b);
        SecureRandom random = new SecureRandom();
        int chave = random.nextInt(900000) + 100000;
        System.out.println("Sua chave de criptografia eh: " + chave);
        b = cifrar(b,chave);
        raf.seek(0);
        raf.writeInt(chave ^ 424242);
        raf.write(b);
        raf.close();
    }//end escreverArquivo

    /*metodo para criptgrafar um arquivo ja existente ( a chave de criptografia sera escrita
    no arquivo para poder validar a mesma na descriptagrafia)*/
    public static void criptografarArquivo() throws Exception{
        System.out.print("Digite o nome do arquivo com extensão: ");
        String name = ""; 
        name = read.nextLine();
        name = read.nextLine();
<<<<<<< HEAD
        RandomAccessFile raf = new RandomAccessFile(name, "rw");
        int tam = (int)raf.length();
        byte[] b = new byte[tam];
        raf.read(b);
        SecureRandom random = new SecureRandom();
        int chave = random.nextInt(900000) + 100000;
        System.out.println("Sua chave de criptografia eh: " + chave);
        b = cifrar(b, chave);
        raf.seek(0);
        raf.writeInt(chave ^ 424242);
        raf.write(b);
        raf.close();
    }//end criptografarArquivo
=======
        File file = new File(name);
        if(file.exists()){
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            int tam = (int)raf.length();
            byte[] b = new byte[tam];
            raf.read(b);
            SecureRandom random = new SecureRandom();
            int chave = random.nextInt(900000) + 100000;
            System.out.println("Sua chave de criptografia eh: " + chave);
            b = cifrar(b, chave);
            raf.seek(0);
            raf.writeInt(chave ^ 424242);
            raf.write(b);
            raf.close();
            System.out.println("Criptografado com sucesso! ");
        }
        else System.out.println("Não há um arquivo com esse nome!\nCriptografia não foi realizada!");
        Thread.sleep(1000);
    }
>>>>>>> 21b4475eb8321dd056c09938b8a02c205dc714e7

    //metodo para descriptografar arquivo criptografado anteriormente a partir de uma chave informada na hora da cifracao
    public static void descriptografarArquivo() throws Exception{
        System.out.print("Digite o nome do arquivo: ");
        String name = ""; 
        name = read.nextLine();
        name = read.nextLine();
        File file = new File(name);
        if(file.exists()){
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            int chave_correta = raf.readInt() ^ 424242;
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

<<<<<<< HEAD
        raf.read(b);
        b = decifrar(b, chave);
        raf.close();
        File f = new File(name);
        f.delete();
        raf = new RandomAccessFile(name, "rw");
        raf.write(b);
        raf.close();
    }//end descriptografarArquivo
=======
            raf.read(b);
            b = decifrar(b, chave);
            raf.close();
            File f = new File(name);
            f.delete();
            raf = new RandomAccessFile(name, "rw");
            raf.write(b);
            raf.close();
            System.out.println("Descritografado com sucesso!");
        }
        else System.out.println("Não há um arquivo com esse nome!\nA descriptografia não foi realizada!");
        Thread.sleep(1000);
    }
>>>>>>> 21b4475eb8321dd056c09938b8a02c205dc714e7

    //metodo de criptografia em que, a cada byte do texto eh somado um byte da chave
    public static byte[] cifrar(byte[] b, int chave){
        for(int i = 0; i < b.length; i++){
            b[i] = (byte)(b[i]+(byte)chave);   
        }
        return b;
    }

    //metodo para descriptografar em que, a cada byte do texto eh subtraido um byte da chave
    public static byte[] decifrar(byte[] b, int chave){
        for(int i = 0; i < b.length; i++){
            b[i] = (byte)(b[i]-(byte)chave);   
        }
        return b;
    }
}// Fim classe Projeto
