package edu.csupomona.cs480.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import com.google.firebase.database.*;
import org.apache.commons.io.IOUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.csupomona.cs480.App;
import edu.csupomona.cs480.data.*;
import edu.csupomona.cs480.data.provider.*;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * <p>
	 * In our project, all the beans are defined in
	 * the {@link App} class.
	 */
	@Autowired
	private UserManager userManager;

	@Autowired
	private FirebaseDatabase firebaseDatabase;

	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs480/ping", method = RequestMethod.GET)
	String healthCheck() {
		// You can replace this with other string,
		// and run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return "OK";
	}

	/**
	 * This is a simple example of how to use a data manager
	 * to retrieve the data and return it as an HTTP response.
	 * <p>
	 * Note, when it returns from the Spring, it will be
	 * automatically converted to JSON format.
	 * <p>
	 * Try it in your web browser:
	 * 	http://localhost:8080/cs480/user/user101
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.GET)
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}


	
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public @ResponseBody ConvertDateTime addNewData(@RequestBody ExtractData obj) {

		MapAvailableScheduler scheduler = new MapAvailableScheduler();
		scheduler.setStart(obj.getStart());
		scheduler.setEnd(obj.getEnd());


		List<MapAvailable> availables = new ArrayList<>();
		String[] users = obj.getUsers();
		int[] counter = new int[1];
		counter[0] = 0;
		for(int i = 0; i < users.length; i++) {
			DatabaseReference ref = firebaseDatabase.getReference("user/" + users[i] + "/available/");

			ref.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					MapAvailable sche = dataSnapshot.getValue(MapAvailable.class);
					availables.add(sche);
					System.out.println("Success");
					counter[0]++;
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
					System.out.println("The read failed: " + databaseError.getCode());
				}
			});
		}
		while(counter[0] != users.length - 1) {

		}

		scheduler.setSchedules(availables);
		System.out.println("Size : "+ scheduler.getSchedules().size());
		ConvertDateTime time = new ConvertDateTime(scheduler.bestAvailableTime());
		System.out.print(time);
		return time;


	}

	/**
	 * This is an example of sending an HTTP POST request to
	 * update a user's information (or create the user if not
	 * exists before).
	 *
	 * You can test this with a HTTP client by sending
	 *  http://localhost:8080/cs480/user/user101
	 *  	name=John major=CS
	 *
	 * Note, the URL will not work directly in browser, because
	 * it is not a GET request. You need to use a tool such as
	 * curl.
	 *
	 * @param id
	 * @param name
	 * @param major
	 * @return
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.POST)
	User updateUser(
			@PathVariable("userId") String id,
			@RequestParam("name") String name,
			@RequestParam(value = "major", required = false) String major) {
		User user = new User();
		user.setId(id);
		user.setMajor(major);
		user.setName(name);
		userManager.updateUser(user);
		return user;
	}

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	@RequestMapping(value = "/cs480/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	@RequestMapping(value = "/cs480/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}

	//Justin's API Call
	@RequestMapping(value = "/cs480/jhan", method = RequestMethod.GET)
	String Test() {
		String message = "<html><body style=\"color: #0000ff; text-white: 3px 3px #000; font-size: 36px;\"><h1>Testtttttttt</h1>"
				+ "<img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBfB_ieMn099EnQHVnGYRJQrY2p9PfxT-taj04FNq60G1CD-KV\" />"
				+ "</body></html>";
		return message;
	}

	//Justin's Assignment 4
	@RequestMapping(value = "/cs480/soup", method = RequestMethod.GET)
	String Assignment4() {
		String message = "";
		Document doc;
		try {
			doc = Jsoup.connect("http://www.espn.com/").get();
			Elements newsHeadlines = doc.select("span.quicklinks_list__name");
			message = newsHeadlines.text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	//Johnny's method
	@RequestMapping(value = "/cs480/johnnylu", method = RequestMethod.GET)
	String msg() {
		return "Johnny was here";
	}

	// Johnny Assignment 4
	@RequestMapping(value = "/cs480/cmsio", method = RequestMethod.GET)
	String getMsg() {
		try {
		 InputStream in = new URL( "http://commons.apache.org" ).openStream();
		 String content = IOUtils.toString(in, "UTF-8");
		   return(content);
		}
		catch (Exception e)
		{
			return e.toString();
		}
	}

	//Jason's API Call
		@RequestMapping(value = "/cs480/leej", method = RequestMethod.GET)
		String Test2() {
			String message = "<!DOCTYPE html>"
					+ "<html>\r\n" +
					"<head>\r\n" +
					"<title> Jason's Page. </title>\r\n" +
					"</head>\r\n" +
					"<body>\r\n" +
					"<h1>Welcome to Jason's Page</h1>\r\n" +
					"<p>My favorite <strong>video games</strong> are:<br>\r\n" +
					"<p>League of Legend<br> <br> Overwatch<p/>\r\n" +
					"<h2>My favorite website<h2>\r\n" +
					" <a href=\"http://youtube.com\">???	\r\n" +
					"</body>\r\n" +
					"</html>";
			return message;
		}

		// Jason's Assignment #4 ;
		@RequestMapping(value = "/cs480/leej_email", method = RequestMethod.GET)
		String send() {

			/*String hostServer = "asdf.ghijk.com";
			String emailID = "notcsmajor1234@gmail.com";
			Properties p = System.getProperties();
			p.put("mail.host", hostServer);
			Session session = Session.getInstance(p,null);
			EmailUtil.sendEmail(session, emailID,"Simple Email Testing Subject","Simple Email Testing Body");
			 */
			return "Working on email sending";
		}

		//Ubaldo's Method Call
		@RequestMapping(value = "user/simplyvaldo", method = RequestMethod.GET, produces = "text/html")
		ModelAndView myHomepage() {

			ModelAndView modelAndView = new ModelAndView("simplyvaldo");
			return modelAndView;
		}

		//Ubaldo's Assignment #4 (Joda-Time dependency)
		@RequestMapping(value = "user/simplyvaldo/name", method = RequestMethod.GET)
		String timeZones() {

			DateTime time1 = new DateTime(System.currentTimeMillis(), DateTimeZone.forID("UTC"));
			DateTime time2 = new DateTime(System.currentTimeMillis(), DateTimeZone.forID("America/Los_Angeles"));
			DateTime time3 = new DateTime(System.currentTimeMillis(), DateTimeZone.forID("America/Chicago"));
			DateTime time4 = new DateTime(System.currentTimeMillis(), DateTimeZone.forID("America/New_York"));
			DateTime time5 = new DateTime(System.currentTimeMillis(), DateTimeZone.forID("Asia/Hong_Kong"));

			return  "<h1>Current Exact Time</h1>" +
					"<b>" + time1.getZone().toString() + "</b>" + ": " + time1.toString(DateTimeFormat.fullDateTime()) + "<br><br>" +
					"<b>" + time2.getZone().toString() + "</b>" + ": " + time2.toString(DateTimeFormat.fullDateTime()) + "<br><br>" +
					"<b>" + time3.getZone().toString() + "</b>" + ": " + time3.toString(DateTimeFormat.fullDateTime()) + "<br><br>" +
					"<b>" + time4.getZone().toString() + "</b>" + ": " + time4.toString(DateTimeFormat.fullDateTime()) + "<br><br>" +
					"<b>" + time5.getZone().toString() + "</b>" + ": " + time5.toString(DateTimeFormat.fullDateTime()) + "<br><br>" ;
		}
}
