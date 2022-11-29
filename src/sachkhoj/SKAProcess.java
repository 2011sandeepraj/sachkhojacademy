package sachkhoj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class SKAProcess {

	public static void main(String[] args) {
		
		//Change pathToFile variable and provide the path of the file that contains all the links.
		String pathToFile = "C:\\Users\\sandeepraj.tandon\\Documents\\Personal\\Gurbani\\SKA - Katha\\SKA-Katha-Links.txt";
		
		//Location of the folder we want the files to be in 
		String pathToDestFolder = "C:\\Users\\sandeepraj.tandon\\Documents\\Personal\\Gurbani\\SKA - Katha";
	
		
		//Location of the folder we want the files to be in 
		String pathToSrcFolder = "C:\\Users\\sandeepraj.tandon\\Documents\\Personal\\Gurbani\\SKA - Katha";
			
		String findPattern = "http://www.SachKhojAcademy.net";
		String findPattern1 = "http://www.sachkhojacademy.net";
		String findPattern2 = "www.SachKhojAcademy.net";
		arrangeFilesToFolders( pathToFile, findPattern,findPattern1, findPattern2,pathToSrcFolder, pathToDestFolder );
		
		deleteMovedFiles(pathToDestFolder);

	}


	private static void deleteMovedFiles(String pathToDestFolder) {
		//Should have cut copied instead of copy pasting. The code below this deletes the left over files that have been put in to accurate folders.	
		File folder = new File(pathToDestFolder);
		File[] listOfFiles = folder.listFiles();
		
		File fileToDelete = null;
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
			  fileToDelete = listOfFiles[i];
			  System.out.println("File to be deleted "+fileToDelete.getName());
			  
			  for (int j = 0; j < listOfFiles.length; j++) {
				   if (listOfFiles[j].isDirectory()) {

					   File[] innerFiles = listOfFiles[j].listFiles();
					   for (int k = 0; k < innerFiles.length; k++) {
							  if (innerFiles[k].isFile()) {
								  if(fileToDelete.getName().equalsIgnoreCase(innerFiles[k].getName())){

									  System.out.println("Found and Deleted the file "+fileToDelete.delete());
								  }
							  
							  }
					   }
				   
				   }
			  }
			  
		  }
		 
		}
	}
	
	
	//Read file content into string with - using BufferedReader and FileReader
    //You can use this if you are still not using Java 8
 
    private static void arrangeFilesToFolders(String filePath, String findPattern, String findPattern1, String findPattern2, String pathSrcFolder, String pathDestFolder )
    {
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
 
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                if(sCurrentLine.contains(findPattern)){
                	sCurrentLine = sCurrentLine.replace(findPattern, "");
                
                        
                } else if(sCurrentLine.contains(findPattern1)){
                	sCurrentLine = sCurrentLine.replace(findPattern1, "");
                } else if(sCurrentLine.contains(findPattern2)){
                	sCurrentLine = sCurrentLine.replace(findPattern2, "");
                }
            	System.out.println(sCurrentLine);
                
                String[] paths = sCurrentLine.split("/");

                File f = new File(pathDestFolder.concat("\\").concat(paths[1]));
                if (!f.exists() ) {
                	f.mkdir() ;
            		        
                }
                
                if(f.exists() && f.isDirectory()){
                	FileChannel sourceChannel = null;
                    FileChannel destChannel = null;
                    try {
                    	File file = new File (pathDestFolder.concat("\\").concat(paths[1]).concat("\\").concat(paths[2]));
                    	if(!file.exists()){
                    	
	                        sourceChannel = new FileInputStream(pathSrcFolder.concat("\\").concat(paths[2])).getChannel();
	                        System.out.println(sourceChannel);
	                        
	                        destChannel = new FileOutputStream(pathDestFolder.concat("\\").concat(paths[1]).concat("\\").concat(paths[2])).getChannel();
	                        System.out.println(destChannel); 
	                        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	                        
	          	                                
		                } else{
		                	 System.out.println(pathDestFolder.concat("\\").concat(paths[1]).concat("\\").concat(paths[2])+" already exists in the desitnation");
		                	 
		                } 
                    	
                    	
                    	
                      } 
                    catch (Exception e){
                    	System.out.println(e.getMessage());
                    }
                    finally{
                           if(sourceChannel != null) sourceChannel.close();
                           if(destChannel != null) destChannel.close();
                   }
                }
                
                
    	
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
