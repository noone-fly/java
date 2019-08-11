package io.agora.recording.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.agora.recording.RecordingSDK;

/**
 * Servlet implementation class LeavechannelServlet
 */
@WebServlet("/LeavechannelServlet")
public class LeavechannelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeavechannelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String nativeHandle=request.getParameter("nativeHandle");
	    System.out.println("==LeavechannelServlet===nativeHandle=="+nativeHandle);
	    RecordingSDK recordingSdk = new RecordingSDK();
        //RecordingSample ars = new RecordingSample(recordingSdk);
	    RecordingSampleM ars = new RecordingSampleM(recordingSdk);
	    ars.leaveChannel(Long.parseLong(nativeHandle));
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
