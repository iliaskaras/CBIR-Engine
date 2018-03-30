package karatsin_ilias.cbir_project;

import karatsin_ilias.cbir_project.Controller.Database.DatabaseConnector;
import karatsin_ilias.cbir_project.Controller.GUI.MainGUI_CBIR_Controller;
//import org.junit.Test;
import java.io.File;


public class App 
{

    private static void initOpenCV_Lib(){
        File lib = null;
        String os = System.getProperty("os.name");
        String bitness = System.getProperty("sun.arch.data.model");
        System.out.println( os + " "+bitness );

        if (os.toUpperCase().contains("WINDOWS")) {
            if (bitness.endsWith("64")) {
                lib = new File("lib//x64//" + System.mapLibraryName("opencv_java2411"));
            } else {
                lib = new File("lib//x86//" + System.mapLibraryName("opencv_java2411"));
            }
        } else {
            lib = new File("lib//x86//" + System.mapLibraryName("opencv_java2411"));
        }

        System.load(lib != null ? lib.getAbsolutePath() : null);

    }

//    @Test
//    public void test(){
//        int a=0;
//        int result = a+2;
//
//    }

    public static void main( String[] args )
    {
        initOpenCV_Lib();

        MainGUI_CBIR_Controller mainGUI_cbir_controller = new MainGUI_CBIR_Controller();
        mainGUI_cbir_controller.showMainGUI_CBIR_Window();

        DatabaseConnector.connectToDatabase();

    }



}

