package enhancer.filehandlers;

import java.io.File;

public class DirectoryCreator {

    public boolean create(String directory){
        File file = new File(directory);
        if (!file.exists()) return file.mkdir();
        return true;
    }

}
