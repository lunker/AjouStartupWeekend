package dk.app.server;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerFileUpload;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;



public class MyHttpServer extends Verticle {
	static final int HTTPPORT = 8787;
//	static final String SERVERIP = "1.234.75.40";
	static final String SERVERIP = "192.168.43.137";

	EventBus eventBus ;
	boolean isRegistered = false;
	
	@Override
	public void start(){
		
		HttpServer server = vertx.createHttpServer();
		eventBus = vertx.eventBus();
		eventBus.registerHandler("httpserver", new Handler<Message>() {

			@Override
			public void handle(Message arg0) {
				// TODO Auto-generated method stub
				System.out.println("in the httpserver");
			}
		});
		
		server.requestHandler(new Handler<HttpServerRequest>() {
			@Override
			public void handle(HttpServerRequest request) {
				// TODO Auto-generated method stub
				
				final HttpServerRequest req = request;
				request.expectMultiPart(true);
				System.out.println("request received ! ");
				final Buffer body = new Buffer(0);
				String method = request.method();
				String filename = "";
				//HTTP GET
				
				if(method.equals("GET")){
					//test
					//send the image to client by http get.
					if(req.headers().get("state").equals("main")){
						
						String path = "/Users/lunker/Desktop/";
						String id = req.headers().get("id");
						String file = "rtest";
						String exten = ".png";
						String target = path + file+id+exten;
						
						System.out.println("in state main, in 'get' " + target);
						
						req.response().headers().add("hi", "dk");
						req.response().sendFile(target, new Handler<AsyncResult<Void>>() {
							@Override
							public void handle(AsyncResult<Void> arg0) {
								// TODO Auto-generated method stub
								System.out.println("image send successful.");
							}
						});
					}//end if
					else if(req.headers().get("state").equals("getdetail")){
						
						int id = Integer.parseInt(req.headers().get("productid"));
						
						JsonObject find = new JsonObject();
						find.putString("action", "find");
						find.putString("collection", "product");
						find.putObject("matcher", new JsonObject().putNumber("productId",  id ));
						eventBus.send("database.my", find, new Handler<Message<JsonObject>>() {
							
							@Override
							public void handle(Message<JsonObject> result) {
								// TODO Auto-generated method stub
								
								System.out.println("find the product info !!");
								System.out.println("result nickname : " + result.body().getArray("results").toString());
								JsonObject tmp =  result.body().getArray("results").get(0);
//								tmp.getString("nickname");
//								System.out.println("result at 1 : " + result.body().getArray("results").get(2));
//								req.response().sendFile(url);
								req.response().headers().add("rentalFrom", tmp.getString("rentalFrom"));
								req.response().headers().add("rentalTo", tmp.getString("rentalTo"));
								req.response().headers().add("rentalPrice", tmp.getNumber("rentalPrice")+"");
//								req.response().headers().add("ps", tmp.getString("ps"));
								req.response().setChunked(true);
//								req.response().headers().add("Content-Length", "1");
								req.response().write(tmp.getString("ps"), "UTF-16");
								req.response().end();
								
							}
						});
						
					}
					
				}// end get
				
				//take the user photo upload
				request.uploadHandler( new Handler<HttpServerFileUpload>() {

					String id = req.headers().get("id");
					
					String inputPath ="";
					@Override
					public void handle(HttpServerFileUpload upload) {
						// TODO Auto-generated method stub
						System.out.println("in the uploadhandler");
						
						}
						
				});
				
				
				request.dataHandler(new Handler<Buffer>() {
					@Override
					public void handle(Buffer buffer) {
						// TODO Auto-generated method stub
						System.out.println("in data handler");
						body.appendBuffer(buffer);
						
					}
				});
				
				request.bodyHandler(new Handler<Buffer>() {
					@Override
					public void handle(Buffer body) {
						// TODO Auto-generated method stub
						System.out.println("in body handler ");
					}
				});
				
				//called at the end of the request
				//take the 'post update part'
				request.endHandler(new Handler<Void>() {
					
					@Override
					public void handle(Void arg0) {
						// TODO Auto-generated method stub
						System.out.println("in end handler ");
						
						if(req.headers().get("state").equals("main")){
							
							System.out.println("in the state main, in endhandler");
							
//							req.response().headers().add("hi", "dk");
//							req.response().write("");
						}
						
					else if(req.headers().get("state")!=null){
							if(req.headers().get("state").equals("update")){
								/*
								System.out.println("get the request from client !");
								MultiMap map = req.formAttributes();
								System.out.println(map.size());
								String id = map.get("id");
								String introduction = map.get("introduction");
								System.out.println(id + introduction);
								
								eventBus.send("database.my",getUpdateJSONObject("user", id, introduction),new Handler<Message>() {

									@Override
									public void handle(Message arg0) {
										// TODO Auto-generated method stub
										System.out.println("update the user info !!" + arg0.body());
									}
								});
								*/
								if(req.headers().get("type").equals("basicprofile")){
									
									System.out.println("user updated!");
									
									MultiMap map = req.formAttributes();
									Long id = Long.parseLong(map.get("id"));
									String nickname = map.get("nickname");
									String profile = map.get("profile");
									
									JsonObject objNew = new JsonObject();
									objNew.putNumber("id",id);
									objNew.putString("nickname", nickname);
									objNew.putString("profile", profile);
									
									JsonObject criteria = new JsonObject();
									criteria.putNumber("id", id);
									
									JsonObject update = new JsonObject();
									update.putString("action","update");
									update.putString("collection", "user");
									update.putObject("criteria", criteria);
									update.putObject("objNew",new JsonObject().putObject("$set", objNew));
									
									eventBus.send("database.my",  update  , new Handler<Message>() {

										@Override
										public void handle(Message arg0) {
											// TODO Auto-generated method stub
											System.out.println("update user nickname, profile");
										}
									});
									
								}
								else if(req.headers().get("type").equals("editphoto")){
									
								}
							}/*
							else if(req.headers().get("state").equals("image")){
								System.out.println("end write image to server folder");
								req.response().setChunked(true);
								req.response().write("ack");
								req.response().end();
							}*/
							else if(req.headers().get("state").equals("register")){
								System.out.println("user register!");
								
								final MultiMap map = req.formAttributes();
								final Long id = Long.parseLong(map.get("id"));
								
								JsonObject find = new JsonObject();
								find.putString("action", "find");
								find.putString("collection","user");
								find.putObject("matcher", new JsonObject().putNumber("id", id));
								
								eventBus.send("database.my",find,new Handler<Message<JsonObject>>() {

									@Override
									public void handle(Message<JsonObject> result) {
										// TODO Auto-generated method stub
										System.out.println("db message");
										if(result.body().getString("status").equals("ok")){
//											System.out.println(result.body().getArray("results").size());
//											
											
											if(result.body().getArray("results").size() == 0){
												System.out.println("user not registered");
												String nickname = map.get("nickname");
												String profile = map.get("profile");
												String registerdate = map.get("registerdate");
												
												JsonObject document = new JsonObject();
												document.putNumber("id", id);
												document.putString("nickname", nickname);
												document.putString("profile", profile);
												document.putNumber("win", 0);
												document.putNumber("lose", 0);
												document.putString("introduction", "");
												document.putString("registerdate", registerdate);
												
												
												JsonObject content = new JsonObject();
												content.putString("action", "save");
												content.putString("collection","user");
												content.putObject("document", document);
												
												eventBus.send("database.my",content,new Handler<Message>() {

													@Override
													public void handle(Message arg0) {
														// TODO Auto-generated method stub
														System.out.println("register user");
														
													
//														req.response().putHeader(arg0, arg1)
														req.response().setChunked(true);
														req.response().write("ack");
														req.response().end();
													}
												});
											}
											else{
												System.out.println("user already registered");
											}
										}
										else{
											System.out.println("user register errored!");
										}
										
									}//end method
								});// end handler
								
								
							}//end elseif
							
							
						}//end if
						
						else if(req.headers().get("signout")!=null){
							if(!req.headers().get("signout").equals("")){

								Long id = Long.parseLong(req.headers().get("signout"));
								
								JsonObject delete = new JsonObject();
								delete.putString("action", "delete");
								delete.putString("collection", "user");
								delete.putObject("matcher", new JsonObject().putNumber("id", id));
								
								eventBus.send("database.my", delete, new Handler<Message>() {
									
									@Override
									public void handle(Message arg0) {
										// TODO Auto-generated method stub
										System.out.println("delete the user from db");
										
									}
								} );
							}
						}//end elseif
					}//END HANDLE
				});//end handler
			}// end handle
		});
		
		server.listen(HTTPPORT, SERVERIP, new Handler<AsyncResult<HttpServer>>() {
			
			@Override
			public void handle(AsyncResult<HttpServer> result) {
				// TODO Auto-generated method stub
				
			 System.out.println("server waiting . . . " + result.succeeded());
			}
		});
	}
	
	public JsonObject makeMongoObject(String action, String collection, JsonObject criteria, JsonObject newObj){
		JsonObject content = new JsonObject();
		
		
		if(action.equals("save")){
			content.putString("action", action);
			content.putString("collection", collection);
			content.putObject("document", newObj);
		}
		else if(action.equals("update")){
		}
		
		return content;
		
		
	}
	public JsonObject getUpdateJSONObject(String collection, Long id, JsonObject objNew){
		

		JsonObject criteria = new JsonObject();
		criteria.putNumber("id", id);
		
		
		JsonObject json = new JsonObject();
		json.putString("action","update");
		json.putString("collection", collection);
		json.putObject("criteria", criteria);
		
		json.putObject("objNew",objNew);
		return json;
	}//end method
}


















