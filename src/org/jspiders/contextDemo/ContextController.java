package org.jspiders.contextDemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/cd")
public class ContextController extends HttpServlet {
	@Override
		public void init(ServletConfig config) throws ServletException {
	
			super.init(config);
		ServletContext ctxt = config.getServletContext();
		}
	
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	ServletContext ctx = req.getServletContext();
	String driver=ctx.getInitParameter("driver");
	String pass = ctx.getInitParameter("pass");
	String user = ctx.getInitParameter("user");
	String url = ctx.getInitParameter("url");
	try {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, pass);
		String qry ="select * from jdbc.logininfo where uname=? and password=?";
		PreparedStatement ps = con.prepareStatement(qry);
		PrintWriter pw= resp.getWriter();
	    String UserId= req.getParameter("un");
		String password=req.getParameter("pass");
		ps.setString(1, UserId);
		ps.setString(2, password);
		ResultSet rs = 	ps.executeQuery();
		if(rs.next()) {
			String out= "<html><body bgcolor=\"yellow\"><h1>Welcome "+rs.getString(3)+"</h1></body></html>";
			pw.println(out);
		}
		else {
			pw.println("Error");
		}
		
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
}
}
