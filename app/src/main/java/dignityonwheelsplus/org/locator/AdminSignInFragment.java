package dignityonwheelsplus.org.locator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AdminSignInFragment extends Fragment {

    private EditText _txtUser, _txtPass;
    private Button loginTo;

    private String userName, pass;

    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View adminView = inflater.inflate(R.layout.fragment_admin, container, false);
        _txtUser = (EditText) adminView.findViewById(R.id.txtUser);
        _txtPass = (EditText) adminView.findViewById(R.id.textPass);

        loginTo = (Button) adminView.findViewById(R.id.btn_login);

        loginTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userName = _txtUser.getText().toString();
                pass = _txtPass.getText().toString();

                if (userName.equals("shelteradmin") && pass.equals("shelteradmin1!")){
                    Intent intent = new Intent (AdminSignInFragment.this.getActivity(), Admin.class);
                    startActivity(intent);
                    _txtUser.setText("");
                    _txtPass.setText("");

                }
                else{
                    Toast.makeText(getContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return adminView;
    }


}
