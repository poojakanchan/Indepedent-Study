package com.aspectj;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

  
public class AspectjLog {

        static boolean startLogging = false;	
	static String traceFile = "/sdcard/AspectJ_call_trace.txt";
	static String methodFile = "/sdcard/AspectJ_method_definitions.txt";

	 static Map<Signature, Integer> signatureMap = new HashMap<Signature, Integer>();
	 static int methodId = 1;
	public static void log( ProceedingJoinPoint pjp, String message, boolean printParamatersFlag) {
                       int processId = android.os.Process.myPid();
                       int threadId =  android.os.Process.getThreadPriority(android.os.Process.myTid());

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

//    	  Files.write(Paths.get(traceFile), logBuilder.toString().getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

     public static void startLogging() {
         startLogging = true;

     }
    
    public static void stopLogging() {
      startLogging = false;
    }
 

}

