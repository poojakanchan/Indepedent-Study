package com.aspectj;
import android.os.Environment;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

  
public class AspectjLog {

        static boolean startLogging = false;	
	/*static String traceFile = "/sdcard/AspectJ_call_trace.txt";
	static String methodFile = "/sdcard/AspectJ_method_definitions.txt";
*/
    static File  traceFile;
    static File methodFile;
	 static Map<Signature, Integer> signatureMap = new HashMap<Signature, Integer>();
	 static int methodId = 1;
    public static void log( ProceedingJoinPoint pjp, String message, boolean printParamatersFlag) {
                     
                    if(startLogging) {  
                     int processId = android.os.Process.myPid();
                       int threadId =  android.os.Process.getThreadPriority(android.os.Process.myTid());

                        int methodIdCurrentProcess = 0;
                        final Signature signature = pjp.getSignature();
                        if (signatureMap.containsKey(signature)) {
                                //       System.out.println("repeated!!");
                                methodIdCurrentProcess = signatureMap.get(signature);
                        } else {
                                StringBuilder logBuilder = new StringBuilder();
                                logBuilder.append(methodId);
                                logBuilder.append("  ");
                                logBuilder.append(signature);
                                logBuilder.append("\n");
                                signatureMap.put(signature, methodId);
                                System.out.println(logBuilder.toString());
 
                                

                                   try {
                                      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(methodFile, true)));
                                      out.print(logBuilder.toString());
                                      out.close();
                                     //Files.write(Paths.get(methodFile), logBuilder.toString().getBytes(), StandardOpenOption.APPEND);
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                      }
                                 
                                methodIdCurrentProcess = methodId;
                                methodId++;
                        }
                        try {
                                StringBuilder logBuilder = new StringBuilder();
                                logBuilder.append(processId);
                                logBuilder.append("   ");
                                logBuilder.append(threadId);
                                logBuilder.append("   ");
                                logBuilder.append(methodIdCurrentProcess);
                                logBuilder.append("   ");
                                logBuilder.append(message);

                                if (printParamatersFlag) {
                                        logBuilder.append("  [");
                                        if (pjp.getArgs().length > 0) {
                                                for (final Object argument : pjp.getArgs()) {
                                                        logBuilder.append(argument + ",");
                                                }
                                                // logBuilder.deleteCharAt(logBuilder.length()-1);
                                        }
                                        logBuilder.append("]");
                                }
                                logBuilder.append("\n");
                                System.out.println(logBuilder.toString());
                               if(startLogging) {
                                  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(traceFile, true)));
                                  out.print(logBuilder.toString());
                                  out.close();
                                }

//        Files.write(Paths.get(traceFile), logBuilder.toString().getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
/*
               ..............................................
			int methodIdCurrentProcess = 0;
			final Signature signature = pjp.getSignature();
			if (signatureMap.containsKey(signature)) {
				//	 System.out.println("repeated!!");
				methodIdCurrentProcess = signatureMap.get(signature);
			} else {
				StringBuilder logBuilder = new StringBuilder();
				logBuilder.append(methodId);
				logBuilder.append("  ");
				logBuilder.append(signature);
				logBuilder.append("\n");
				signatureMap.put(signature, methodId);
				System.out.println(logBuilder.toString());
                if(startLogging) {

                    try {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(methodFile, true)));
                        out.print(logBuilder.toString());
                        out.close();
                        //Files.write(Paths.get(methodFile), logBuilder.toString().getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
				methodIdCurrentProcess = methodId;
				methodId++;
			}
			try {
				StringBuilder logBuilder = new StringBuilder();
				logBuilder.append(processId);
				logBuilder.append("   ");
				logBuilder.append(methodIdCurrentProcess);
				logBuilder.append("   ");
				logBuilder.append(message);

				if (printParamatersFlag) {
					logBuilder.append("  [");
					if (pjp.getArgs().length > 0) {
						for (final Object argument : pjp.getArgs()) {
							logBuilder.append(argument + ",");
						}
						// logBuilder.deleteCharAt(logBuilder.length()-1);
					}
					logBuilder.append("]");
				}
				logBuilder.append("\n");
				System.out.println(logBuilder.toString());
                if(startLogging) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(traceFile, true)));
                    out.print(logBuilder.toString());
                    out.close();
                }

//    	  Files.write(Paths.get(traceFile), logBuilder.toString().getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
*/
          }
	}

     public static void startLogging(String fileName) {
         startLogging = true;

           System.out.println("########## ASPECTLOG FROM SYSTEM!!!");
         if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
             //handle case of no SDCARD present
              System.out.println("**************************NO SDCARD !! ************************");
         } else {
             System.out.println("************************** SDCARD PRESENT !! ************************");
          
              String dir = "/sdcard";
           try{
 
               dir = Environment.getExternalStorageDirectory().getAbsolutePath();
             }catch(Error e) {
               System.out.println(e);
             }
            dir = dir + File.separator + "logs";
             //create folder
             File folder = new File(dir); //folder name
             folder.mkdirs();

             //create file
              traceFile = new File(dir,fileName +   "_call_trace.txt");
             methodFile = new File(dir, fileName + "_method_definitions.txt");
              try {
              PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(methodFile, false)));
              out.print("Method Definitions: \n");
              out.close();
              out = new PrintWriter(new BufferedWriter(new FileWriter(traceFile, false)));
              out.print("Call Trace: \n");
              out.close();
              }catch(IOException io) {
                io.printStackTrace();
              }
         }
     }
    
    public static void stopLogging() {

      startLogging = false;
    }
 

}

