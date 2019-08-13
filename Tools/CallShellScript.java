/**
* export JAVA_HOME=/www/jdk1.8.0. ***
* export JRE_HOME=${JAVA_HOME}/jre
* export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
* export LD_LIBRARY_PATH=/usr/lib
* export PATH=${JAVA_HOME}/bin:${LD_LIBRARY_PATH}:$PATH
* java AgoraJavaRecording --appId "$1" --channelKey "$2" --uid 0 --channel "$3" --appliteDir "$4" --isMixingEnabled 1 --mixedVideoAudio 1 --idle 3 --recordFileRootDir "$5" --lowUdpPort 40000 --highUdpPort 41000;
 */

private void record(String channel, Integer id) throws Exception{
    System.loadLibrary("/../../librecording.so");
    ProcessBuilder pd = new ProcessBuilder("./record.sh","<your appid>","<uid>","<channelname>","<key>");
    pd.directory(new File("filename"));
    int runningStatus = 0;
    String str = null;
    try{
        Process p = pd.start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while((s = stdInput.readLine()) != null){
            System.out.print(s);
        }
        while((s = stdError.readLine()) != null){
            System.out.print(s);
        }
        try{
            runningStatus = p.waitFor();
        }catch(InterruptedException e){

        }
    }catch(IOException e){

    }
    if(runningStatus != 0){

    }
    return;
}