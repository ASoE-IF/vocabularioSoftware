package Vocabulary.Extractor.Util;

public class MemoryRuntimeIFPB {
	
	public void CalculateAll(){
	    // Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    runtime.gc();
	    
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	   
	   System.out.println("Used memory is megabytes: "
	     + bytesToMegabytes(memory));
	   
	   System.out.println("Used memory is Kbytes: "
			     + bytesToKbytes(memory));
	   
	   memory = runtime.maxMemory();
	   System.out.println("M�ximo de mem�ria utilizada em Kbytes: " + bytesToKbytes(memory));

	   long processor = runtime.availableProcessors();
	   System.out.println("N�mero de processadores ativos: " + processor);  
	}
	
	  public long bytesToMegabytes(long bytes) {
		  final long MEGABYTE = 1024L * 1024L;
		  return bytes / MEGABYTE;
		  }
	  
	  public long bytesToKbytes(long bytes) {
		final long KBYTE = 1024L;
	    return bytes / KBYTE;
		}

}