package dignityonwheelsplus.org.locator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
    private Button toSchedule;
    private View.OnClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView =  inflater.inflate(R.layout.fragment_home, container, false);

        toSchedule = (Button) homeView.findViewById(R.id.schedule_btn);
        toSchedule.setOnClickListener(listener);

        return homeView;
    }

    public void setButtonOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}

