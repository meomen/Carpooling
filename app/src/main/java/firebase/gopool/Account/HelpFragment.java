package firebase.gopool.Account;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import firebase.gopool.R;

public class HelpFragment extends AppCompatActivity {

    private static final String TAG = "HelpFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_help);
        Log.d(TAG, "onCreate: started.");

        //Setup back arrow for navigating back to 'ProfileActivity'
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
