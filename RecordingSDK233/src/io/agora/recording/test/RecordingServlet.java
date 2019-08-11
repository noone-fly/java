package io.agora.recording.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.agora.recording.RecordingSDK;

/**
 * Servlet implementation class RecordingServlet
 */
@WebServlet("/RecordingServlet")
public class RecordingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String channelName=request.getParameter("channelName");
	    System.out.println(channelName);
	    RecordingSDK recordingSdk = new RecordingSDK();
        //RecordingSample ars = new RecordingSample(recordingSdk);
	    RecordingSampleM ars = new RecordingSampleM(recordingSdk);
        String[] args = new String[] {"--appId","","--uid","0",
        		"--appliteDir","/home/pierre/Desktop/tool/Agora_Recording233/bin", "--channel",channelName};
        ars.createChannel(args);
        ars.unRegister();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
