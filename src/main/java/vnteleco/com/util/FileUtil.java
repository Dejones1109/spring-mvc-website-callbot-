package vnteleco.com.util;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static HashMap<String, String> doUpload(MultipartFile file, String path) {
		HashMap<String, String> ret = new HashMap();
		FileOutputStream outputStream = null;
		if ((file != null) && (!file.isEmpty()) && (file.getSize() > 0L)) {
			System.out.println("file.getOriginalFilename()=" + file.getOriginalFilename());

			File f1 = new File(path);
			if (!f1.exists()) {
				if (f1.mkdirs()) {
					f1.setWritable(true);
				} else {
					ret.put("error", "Can't Create Folder to Save File!");
					System.out.println("FilePath:" + path);
					return ret;
				}
			} else {
				f1.setWritable(true);
			}
			
			String fileName = file.getOriginalFilename().split("\\.")[0] + DateUtil.datetoString(new Date(), "yyyyMMddhhmmssSSS") + "." + file.getOriginalFilename().split("\\.")[1];
			fileName = path + Normalizer.normalize(fileName, Normalizer.Form.NFD).replaceAll("[^a-zA-Z0-9_.]", "");
			ret.put("filepath", fileName);
			try {
				System.out.println("{DoUpload}{file=" + fileName + "}");
				outputStream = new FileOutputStream(new File(fileName));
				try {
					outputStream.write(file.getBytes());
					System.out.println("{DoUpload}{file=" + fileName + "}{success}");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Can't Upload File!");
					ret.put("error", "Can't Upload File!");
					return ret;
				}
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Can't Upload File!");
					ret.put("error", "Can't Upload File!");
					return ret;
				}
				return ret;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("File Not Found!");
				ret.put("error", "File Not Found!");
				return ret;
			}
			
		}
		return ret;
	}

	public static void copyFile(File source, File dest) throws IOException {
		if (!dest.exists()) {
			dest.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(dest);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Khong  cop dc");
		} finally {
			in.close();
			out.close();
		}
	}

	public static void copyDirectory(File sourceDir, File destDir) throws IOException {
		if (!destDir.exists()) {
			destDir.setWritable(true);
			destDir.mkdirs();
		} else {
			destDir.setWritable(true);
		}
		File[] children = sourceDir.listFiles();
		for (File sourceChild : children) {
			String name = sourceChild.getName();
			File destChild = new File(destDir, name);
			if (sourceChild.isDirectory()) {
				copyDirectory(sourceChild, destChild);
			} else {
				copyFile(sourceChild, destChild);
			}
		}
	}

	public static boolean delete(File resource) throws IOException {
		if (resource.isDirectory()) {
			File[] childFiles = resource.listFiles();
			for (File child : childFiles) {
				delete(child);
			}
		}
		return resource.delete();
	}

	public static void deleteFile(String folder, String ext) {
		GenericExtFilter filter = new GenericExtFilter(ext);
		File dir = new File(folder);

		String[] list = dir.list(filter);
		if (list.length == 0) {
			return;
		}
		for (String file : list) {
			String temp = folder + File.separator + file;
			File fileDelete = new File(temp);
			fileDelete.delete();
		}
	}
	
	public static List<String> findFoldersInDirectory(String directoryPath) {
	    File directory = new File(directoryPath);
		
	    FileFilter directoryFileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isDirectory();
	        }
	    };
			
	    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
	    List<String> foldersInDirectory = new ArrayList<String>(directoryListAsFile.length);
	    for (File directoryAsFile : directoryListAsFile) {
	        foldersInDirectory.add(directoryAsFile.getName());
	    }

	    return foldersInDirectory;
	}
	
	public static String encodedFile(String directoryPath) {
		File file = new File(directoryPath);
		String encodedString = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			int b;
			byte[] buffer = new byte[1024];
			while((b=fis.read(buffer))!=-1){
			   bos.write(buffer,0,b);
			}
			byte[] fileBytes=bos.toByteArray();
			fis.close();
			bos.close();

			byte[] encoded=Base64.encodeBase64(fileBytes);
			encodedString = new String(encoded);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encodedString;
	}

	public static class GenericExtFilter implements FilenameFilter {
		private String ext;

		public GenericExtFilter(String ext) {
			this.ext = ext;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith(this.ext);
		}
	}
	
}
