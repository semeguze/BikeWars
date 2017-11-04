package co.edu.javeriana.bikewars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import co.edu.javeriana.bikewars.Auxiliar.Constants;
import co.edu.javeriana.bikewars.ChatView;
import co.edu.javeriana.bikewars.Logic.Entities.dbObservable;
import co.edu.javeriana.bikewars.R;

/**
 * Created by Todesser on 30/10/2017.
 */

public class FriendAdapter extends ArrayAdapter<dbObservable>{
    public FriendAdapter(@NonNull Context context, int resource, @NonNull List<dbObservable> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.friend_layout, parent, false);
        }
        final ImageView photo = convertView.findViewById(R.id.friendPhoto);
        TextView name = convertView.findViewById(R.id.friendName);
        ImageButton sendMessage = convertView.findViewById(R.id.friendSendMessage);
        ImageButton removeFriend = convertView.findViewById(R.id.friendRemove);
        final dbObservable model = getItem(position);
        photo.setImageResource(R.drawable.ic_account);
        model.setContainer(photo);
        name.setText(model.getDisplayName());
        sendMessage.setImageResource(R.drawable.ic_envelope);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(getContext(), ChatView.class);
                chatIntent.putExtra("photo", model.getPhotoBmp());
                chatIntent.putExtra("name", model.getDisplayName());
                chatIntent.putExtra("UserID", model.getUserID());
                getContext().startActivity(chatIntent);
            }
        });
        removeFriend.setImageResource(R.drawable.cancel);
        removeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.usersRoot + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/friends/");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> friends = dataSnapshot.getValue(List.class);
                        friends.remove(model.getUserID());
                        ref.setValue(friends);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        return convertView;
    }
}
