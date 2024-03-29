//Amy Wickham 12178502
//COIT132229 Assignmemt 1: client / server

package TCP;


import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author amywi
 */
public class MemberListUpdaterImpl implements MemberListUpdater {

    public MemberListUpdaterImpl(TCPServer server, Member memberDetails) {
    }

    public void updateMemberList(Member memberDetails) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TCPServer.addToMemberListObject(memberDetails);
                 System.out.println("This runs every 2 seconds");
            }
            
        }, 0, 2000); 
    }

   
  
}
