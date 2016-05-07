package android.os;

public class Process{
/**
     * Returns the identifier of the calling thread, which be used with
     * {@link #setThreadPriority(int, int)}.
     */
    public static final int myTid(){
       return 1;
 
  }

    /**
     * Returns the process ID of this process.
     */
    public static final int myPid(){
       return 1;
    }
    /**
     * Return the current priority of a thread, based on Linux priorities.
     * 
     * @param tid The identifier of the thread/process to change.
     * 
     * @return Returns the current priority, as a Linux priority level,
     * from -20 for highest scheduling priority to 19 for lowest scheduling
     * priority.
     * 
     * @throws IllegalArgumentException Throws IllegalArgumentException if
     * <var>tid</var> does not exist.
     */
    public static final int getThreadPriority(int tid)
            throws IllegalArgumentException {
         return 1;
     }

}
