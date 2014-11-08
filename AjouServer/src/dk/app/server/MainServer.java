package dk.app.server;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class MainServer extends Verticle {

	static final int HTTPPORT = 888;
//	static final String SERVERIP = "1.234.75.40";
	 static final String SERVERIP = "172.30.90.235";

	EventBus eventBus;

	@Override
	public void start() {

		HttpServer server = vertx.createHttpServer();
		eventBus = vertx.eventBus();
		
		server.requestHandler(new Handler<HttpServerRequest>() {
			
			@Override
			public void handle(HttpServerRequest request) {
				// TODO Auto-generated method stub
				
			request.expectMultiPart(true);
			final HttpServerRequest req = request;
			
				if(req.method() == "GET"){
					if(req.headers().get("state").equals("isregistered")){
						
						String email = req.headers().get("email");
						
						JsonObject find = new JsonObject();
						find.putString("action", "find");
						find.putString("collection","user");
						find.putObject("matcher", new JsonObject().putString("email", email));
						
						eventBus.send("database.my",find,new Handler<Message<JsonObject>>() {

							@Override
							public void handle(Message<JsonObject> result) {
								// TODO Auto-generated method stub
								System.out.println("db message");
								if(result.body().getString("status").equals("ok")){
//									System.out.println(result.body().getArray("results").size());
									
									//send registered ack
									req.response().setChunked(true);
									req.response().write("ack");
									req.response().end();
								}
							}
						});//end eventbus
					}// end if 
				}// end get method
				
				req.endHandler(new Handler<Void>() {

					@Override
					public void handle(Void arg0) {
						// TODO Auto-generated method stub
					
						if(req.headers().contains("action")){
							if(req.headers().get("action").equals("register")){
								System.out.println("in register");
								
								JsonObject dbstats = new JsonObject();
								dbstats.putString("action", "collection_stats");
								dbstats.putString("collection", "user");
								eventBus.send("database.my", dbstats, new Handler<Message<JsonObject>>() {

									@Override
									public void handle(Message<JsonObject> result) {
										// TODO Auto-generated method stub
										if(result.body().getString("status").equals("ok")){
											System.out.println(result.body().getObject("stats").toString());
											final int size = result.body().getObject("stats").getInteger("count");
											
											String email = req.formAttributes().get("email");
											String name = req.formAttributes().get("name");
											String pwd = req.formAttributes().get("password");
											int totalWalked = 0;
											int treeWalked = 0;
											int momentum = 0;
											
											JsonObject document = new JsonObject();
											document.putNumber("id",size+1);
											document.putString("name", name);
											document.putString("email", email);
											document.putString("password", pwd);
											document.putNumber("totalwalked", totalWalked);
											document.putNumber("treeWalked", treeWalked);
											document.putNumber("momentum", momentum);

											JsonObject register = new JsonObject();
											register.putString("action", "save");
											register.putString("collection","user");
											register.putObject("document", document);
											
											eventBus.send("database.my",register,new Handler<Message<JsonObject>>() {

												@Override
												public void handle(Message<JsonObject> result) {
													// TODO Auto-generated method stub
													System.out.println(result.body().toString());
												
													req.response().setChunked(true);
													req.response().write(size+1+"");
													req.response().end();
													
													System.out.println("register the user");
													
													JsonObject command = new JsonObject();
													command.putString("action", "command");
													command.putString("command","{create: " +"\"" + (size+1) +"\"}");
												
													eventBus.send("database.my", command, new Handler<Message<JsonObject>>() {

														@Override
														public void handle(
																Message<JsonObject> result) {
															// TODO Auto-generated method stub
															System.out.println("do command result " + result.body().toString());
														}
													});
												}
											});
										}//end if
									}
								});// end eventbus
							}
							else if(req.headers().get("action").equals("workout")){
								
								MultiMap map = req.formAttributes();
								final String id = map.get("id");
								String calorie = map.get("calorieburn");
								final String walkCount = map.get("walkcount");
								String walkedH = map.get("walkedh");
								String walkedM = map.get("walkedm");
								String walkedC = map.get("walkedc");
								String walkedDate = map.get("walkedDate");
								
								JsonObject document = new JsonObject();
								document.putString("calorie", calorie);
								document.putString("walkcount",walkCount);
								document.putString("walkedH", walkedH);
								document.putString("walkedM",walkedM);
								document.putString("walkedC",walkedC);
								document.putString("walkedDate", walkedDate);
								
								JsonObject save = new JsonObject();
								save.putString("action","save");
								save.putString("collection", id);
								save.putObject("document", document);
								
								
								// save the workout
								eventBus.send("database.my", save, new Handler<Message<JsonObject>>() {

									@Override
									public void handle(
											Message<JsonObject> result) {
										// TODO Auto-generated method stub
										System.out.println("do save the workout" + result.body().toString());
									}
								});
								
								// update the user record
								
								JsonObject matcher = new JsonObject();
								matcher.putNumber("id", Integer.parseInt(id));
								
								JsonObject find = new JsonObject();
								find.putString("action", "find");
								find.putString("collection", "user");
								find.putObject("matcher", matcher);
								eventBus.send("database.my", find, new Handler<Message<JsonObject>>() {

									@Override
									public void handle(
											Message<JsonObject> result) {
										// TODO Auto-generated method stub
										System.out.println("find user record");
										System.out.println("find user id : " + id);
										System.out.println(result.body().getArray("results"));
											JsonObject user = result.body().getArray("results").get(0);
											int walk =  Integer.parseInt(walkCount);
											int totalWalked = (int) user.getInteger("totalwalked") + walk;
											int treeWalked = (int) user.getInteger("treeWalked") ;
											
											if( (walk / 100)> 100)
												treeWalked += walk;
											else
												treeWalked += walk / 100;
											
											JsonObject update = new JsonObject();
											update.putString("action", "update");
											update.putString("collection", "user");
											update.putObject("criteria", new JsonObject().putString("id", id));
											update.putObject("objNew", 
													new JsonObject().putObject("$set", 
															new JsonObject().
															putNumber("totalwalked", totalWalked).
															putNumber("treeWalked", treeWalked)));
											
											eventBus.send("database.my", update, new Handler<Message<JsonObject>>() {

												@Override
												public void handle(
														Message<JsonObject> arg0) {
													// TODO Auto-generated method stub
													System.out.println("update the user work record");
												}
											});
									}
								});
							}// end elseif
						}//end if
					}
				});//end handler
			}
		});//end request handler
		
		
		
		server.listen(HTTPPORT,SERVERIP,
				new Handler<AsyncResult<HttpServer>>() {

					@Override
					public void handle(AsyncResult<HttpServer> result) {
						// TODO Auto-generated method stub
						System.out.println("server waiting . . . "
								+ result.succeeded());
					}
				});
	}
}
