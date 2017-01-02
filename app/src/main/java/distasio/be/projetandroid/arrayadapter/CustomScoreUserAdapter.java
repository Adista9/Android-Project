package distasio.be.projetandroid.arrayadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import distasio.be.projetandroid.R;
import distasio.be.projetandroid.User;
import distasio.be.projetandroid.asynctask.CustomScoreUser;

/**
 * Created by Anthony on 02-01-17.
 */

public class CustomScoreUserAdapter extends ArrayAdapter<CustomScoreUser> {
    public CustomScoreUserAdapter(Context context, ArrayList<CustomScoreUser> customScoreUsers) {
        super(context, 0, customScoreUsers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomScoreUser customScoreUser = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_user_score_list, parent, false);

        TextView tv_name_game = (TextView) convertView.findViewById(R.id.tv_name_game_list);
        TextView tv_username = (TextView) convertView.findViewById(R.id.tv_username_list);
        TextView tv_score_max = (TextView) convertView.findViewById(R.id.tv_score_max_list);

        tv_name_game.setText(customScoreUser.getGameName());
        tv_username.setText(customScoreUser.getUsername());
        tv_score_max.setText(customScoreUser.getScore() + "");

        return convertView;
    }
}