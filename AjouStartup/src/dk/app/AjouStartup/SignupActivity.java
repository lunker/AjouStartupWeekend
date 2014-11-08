package superapp.korea.imagestealer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.kakao.APIErrorResult;
import com.kakao.KakaoTalkHttpResponseHandler;
import com.kakao.KakaoTalkProfile;
import com.kakao.KakaoTalkService;
import com.kakao.LoginActivity;
import com.kakao.MeResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.kakao.helper.Logger;

public class SignupActivity extends Activity {

	GlobalVariable global ;
	SharedPreferences prefs ;
	ArrayList<String> adapterList;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		global = (GlobalVariable) getApplicationContext();
		adapterList = new ArrayList<String>();
		prefs = getSharedPreferences( "user", MODE_PRIVATE);
		requestMe();
	}

	/**
	 * 자동가입앱인 경우는 가입안된 유저가 나오는 것은 에러 상황.
	 */
	protected void showSignup() {
		Logger.getInstance().d("not registered user");
		
//		Editor edit = prefs.edit();
////		edit.putInt("signup", value)
//		edit.putBoolean("signup", true);
//		edit.commit();
		redirectLoginActivity();
	}

	protected void redirectMainActivity() {
		
		final Intent intent = new Intent(this, MainActivity.class);
//		intent.putExtras(b);
		startActivity(intent);
		finish();
	}

	protected void redirectLoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
	 */
	private void requestMe() {
		UserManagement.requestMe(new MeResponseCallback() {

			@Override
			protected void onSuccess(final UserProfile userProfile) {
				Logger.getInstance().d("UserProfile : " + userProfile);
				userProfile.saveUserToCache();

				Log.i("clientApp", userProfile.getThumbnailImagePath());
				
				if(prefs.getBoolean("signup", false)){
					
					Log.i("clientApp", "in the signed up ");
					
					KakaoTalkService.requestProfile(new KakaoTalkHttpResponseHandler<KakaoTalkProfile>() {
						
						@Override
						protected void onHttpSuccess(KakaoTalkProfile resultObj) {
							// TODO Auto-generated method stub
							final KakaoTalkProfile profile = resultObj;
							
							if( !prefs.getString("nickname", "").equals(profile.getNickName()) ){
								prefs.edit().putString("nickname", profile.getNickName()).commit();
								Log.i("clientApp", prefs.getString("nickname", ""));
							}
							else if( !prefs.getString("profile", "").equals(profile.getThumbnailURL())){
//							else if( prefs.getString("profile", "").equals(profile.getThumbnailURL())){
								Log.i("clientApp", "before update the user profile");
								prefs.edit().putString("profile", profile.getThumbnailURL()).commit();
								
								Thread getProfileImage = new Thread(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										
										HttpClient client = new DefaultHttpClient();
										HttpGet get = new HttpGet(profile.getThumbnailURL());
										
										try {
											HttpResponse response = client.execute(get);
											
											Bitmap bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
											String filePath = getFilesDir().getAbsolutePath() + "profile.jpg";
											prefs.edit().putString("profilelocal", filePath).commit();
											SaveBitmapToFileCache(bitmap, filePath);
											Log.i("clientApp", "profile cache image path : " + filePath);
											Log.i("clientApp", "write profile image to device");
											
											
//											String SERVER = "http://192.168.25.60:8889";
											String SERVER = "http://" + global.getSERVERIP() + ":" + global.getHTTPPORT();
											HttpClient httpClient = new DefaultHttpClient();
											HttpPost post = new HttpPost(SERVER);
											try {
												MultipartEntityBuilder meb = MultipartEntityBuilder.create();
												meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
										      
												File file = new File(filePath);
												
												meb.addBinaryBody("myprofile", file, ContentType.MULTIPART_FORM_DATA, ".jpg");
												
												Log.i("client", ContentType.DEFAULT_BINARY+"");
										        HttpEntity entity = meb.build();  
										        post.addHeader("id",prefs.getLong("id", -1)+"");
										        post.addHeader("state", "profile");
										        post.setEntity(entity);
										        
										        Log.i("clientApp", "send the request(image) to server ");
										        HttpResponse response2 = httpClient.execute(post);
										        Log.i("clientApp", "get the response(image) from server ");
										    } catch (IOException e) {
										        e.printStackTrace();
										    }
											
											
											
										} catch (ClientProtocolException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
								});
								getProfileImage.start();
							}
							
							
							Thread updateThread = new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									HttpClient client = new DefaultHttpClient();
									HttpPost post = new HttpPost("http://"+global.getSERVERIP()+":"+ global.getHTTPPORT());
									
									MultipartEntityBuilder meb = MultipartEntityBuilder.create();
									meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
									
									meb.addTextBody("id", prefs.getLong("id", -1)+"");
									meb.addTextBody("nickname",profile.getNickName());
									meb.addTextBody("profile",profile.getThumbnailURL());
									

									HttpEntity entity = meb.build();
									post.setHeader("state", "update");
									post.addHeader("type", "basicprofile");
									post.setEntity(entity);
									try {
										HttpResponse response = client.execute(post);
										response.getEntity().getContent();
										Log.i("clientApp", "get response : update the user info");
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
							updateThread.start();
							
							
						}//end method
						
						@Override
						protected void onHttpSessionClosedFailure(APIErrorResult errorResult) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						protected void onNotKakaoTalkUser() {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						protected void onFailure(APIErrorResult errorResult) {
							// TODO Auto-generated method stub
							
						}
					});
				}
				
				else{
					Log.i("clientApp", "in the not sign up ");
					/*
					 * save userprofile local
					 */
					Editor edit = prefs.edit();
					edit.putLong("id", userProfile.getId());
					edit.putString("nickname", userProfile.getNickname());
					edit.putString("profile", userProfile.getThumbnailImagePath());
					edit.commit();
					
					// save userprofile remote
					Thread signupThread = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							/*
							 * prefs에 nickname, profile 저장 !! 
							 */
							HttpClient client = new DefaultHttpClient();
							HttpPost post = new HttpPost("http://" + global.getSERVERIP()+":"+ global.getHTTPPORT());
							
							MultipartEntityBuilder meb = MultipartEntityBuilder.create();
							meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
							meb.addTextBody("id", userProfile.getId()+"",ContentType.MULTIPART_FORM_DATA);
							meb.addTextBody("nickname",userProfile.getNickname(),ContentType.MULTIPART_FORM_DATA);
							meb.addTextBody("profile",userProfile.getThumbnailImagePath(), ContentType.MULTIPART_FORM_DATA);
							
							SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							meb.addTextBody("registerdate", sdfNow.format(new Date(System.currentTimeMillis())));
							
							HttpEntity entity = meb.build();
							post.setHeader("state", "register");
							post.setEntity(entity);
							try {
								Log.i("clientApp", "send the register request ");
								HttpResponse response = client.execute(post);
								response.getEntity().getContent();
								Log.i("clientApp", "get response : register the user info");
								prefs.edit().putBoolean("signup", true).commit();
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					signupThread.start();
					
					Thread getProfileImage = new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							HttpClient client = new DefaultHttpClient();
							HttpGet get = new HttpGet(userProfile.getThumbnailImagePath());
							
							try {
								HttpResponse response = client.execute(get);

								Bitmap bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
								String filePath = getFilesDir().getAbsolutePath() + "profile.jpg";
								prefs.edit().putString("profilelocal", filePath).commit();
								SaveBitmapToFileCache(bitmap, filePath);
								Log.i("clientApp", "write profile image to device");
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
					});
					getProfileImage.start();
					
					
					prefs.edit().putBoolean("signup", true).commit();
					
				}
//				Log.i("clientApp", userProfile.get)
				Thread getRanking = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpClient client = new DefaultHttpClient();
						HttpGet get = new HttpGet("http://" + global.getSERVERIP() +":" + global.getHTTPPORT());
						get.addHeader("myRequest", "getranking");
						
						try {
							Log.i("clientApp", "request the raning list");
							HttpResponse response = client.execute(get);
//							DataInputStream dis = new DataInputStream(response.getEntity().getContent());
							String responseBody = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
							
							Log.i("clientApp", "get the raning list");
							Log.i("clientApp", responseBody);
							
							String[] responses = responseBody.split(",");
//							ArrayList<String> adapterList = new ArrayList<String>();
							
							for(String item : responses){
								adapterList.add(item);
							}
							
							File file = new File(getFilesDir()+"ranking.txt");
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
							oos.writeObject(adapterList);
							oos.close();
							oos = null;
							
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
				});
				getRanking.start();
				
				redirectMainActivity();
				
				/*
				 * hmm?
				 */
				
			}

			@Override
			protected void onNotSignedUp() {
				prefs.edit().putBoolean("signup", false).commit();
				showSignup();
			}

			@Override
			protected void onSessionClosedFailure(
					final APIErrorResult errorResult) {
				redirectLoginActivity();
			}

			@Override
			protected void onFailure(final APIErrorResult errorResult) {
				String message = "failed to get user info. msg=" + errorResult;
				Logger.getInstance().d(message);
				redirectLoginActivity();
			}
		});
	}
	
	private void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {
        
        File fileCacheItem = new File(strFilePath);
        OutputStream out = null;
 
        try
        {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
 
            bitmap.compress(CompressFormat.JPEG, 100, out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
  }
}
