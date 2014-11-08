package dk.app.AjouStartup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.exception.KakaoException;
import com.kakao.widget.LoginButton;

public class LoginActivity extends Activity{

	private LoginButton loginButton;
    private final SessionCallback mySessionCallback = new MySessionStatusCallback();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loginactivity);
        // 로그인 버튼을 찾아온다.
        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 세션을 초기화 한다. 카카오톡으로만 로그인을 유도하고 싶다면 Session.initializeSession(this, mySessionCallback, AuthType.KAKAO_TALK)
        if(Session.initializeSession(this, mySessionCallback)){
            // 1. 세션을 갱신 중이면, 프로그레스바를 보이거나 버튼을 숨기는 등의 액션을 취한다
            loginButton.setVisibility(View.GONE);
        } else if (Session.getCurrentSession().isOpened()){
            // 2. 세션이 오픈된된 상태이면, 다음 activity로 이동한다.
            onSessionOpened();
        }
            // 3. else 로그인 창이 보인다.
    }

    private class MySessionStatusCallback implements SessionCallback {
        @Override
        public void onSessionOpened() {
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈후 보일 페이지로 이동
            LoginActivity.this.onSessionOpened();
            Log.i("clientApp","in onsessionopend()");
        }

        @Override
        public void onSessionClosed(final KakaoException exception) {
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈을 못했으니 다시 로그인 버튼 노출.
            loginButton.setVisibility(View.VISIBLE);
        }

    }

    protected void onSessionOpened(){
        final Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
    

    protected void setBackground(Drawable drawable) {
        final View root = findViewById(android.R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            root.setBackground(drawable);
        } else {
            root.setBackgroundDrawable(drawable);
        }
    }
    
    
}
