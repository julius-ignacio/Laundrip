    package com.example.laundrip;

    import android.content.Intent;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.TextView;

    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInClient;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    public class AccountFragment extends Fragment {

        private FirebaseAuth auth;
        private GoogleSignInClient gsc;
        private TextView fullNameTextView, accountTypeTextView, addressTextView;
        private Button logoutBtn, changePasswordBtn, changeAddressBtn;

        public AccountFragment() {
            // Required empty public constructor
        }

        public static AccountFragment newInstance(String param1, String param2) {
            AccountFragment fragment = new AccountFragment();
            Bundle args = new Bundle();
            args.putString("param1", param1);
            args.putString("param2", param2);
            fragment.setArguments(args);
            return fragment; // Add this return statement
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            auth = FirebaseAuth.getInstance();
            gsc = GoogleSignIn.getClient(getActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_account, container, false);

            fullNameTextView = view.findViewById(R.id.fullNameTextView);
            accountTypeTextView = view.findViewById(R.id.accountTypeTextView);
            logoutBtn = view.findViewById(R.id.logoutBtn);
            changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
            changeAddressBtn = view.findViewById(R.id.changeAddressBtn);
            addressTextView = view.findViewById(R.id.AddressTextView);



            FirebaseUser user = auth.getCurrentUser();

            // Display the user's address

            if (user != null) {
                String userId = user.getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                databaseReference.child(userId).child("address").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().getValue() != null) {
                        String address = task.getResult().getValue(String.class);
                        addressTextView.setText(address);
                    } else {
                        addressTextView.setText("No address available");
                    }
                });
            } else {
                addressTextView.setText("Guest");
            }

            // Display the user's acc type
            if (user != null) {
                String userId = user.getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                databaseReference.child(userId).child("accountType").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().getValue() != null) {
                        String type = task.getResult().getValue(String.class);
                        accountTypeTextView.setText(type);
                    } else {
                        accountTypeTextView.setText("No type set");
                    }
                });
            } else {
                addressTextView.setText("Guest");
            }

            // Display the user's name

            if (user != null) {
                fullNameTextView.setText(user.getDisplayName() != null ? user.getDisplayName() : "User");
            } else {
                fullNameTextView.setText("Guest");
            }

            // Change Address button functionality
            changeAddressBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChangeAddressActivity.class);
                    startActivity(intent);
                }
            });


            // Logout button functionality
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });

            // Change password button functionality (placeholder)
            changePasswordBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                    startActivity(intent);
                }
            });


            return view;
        }

        private void signOut() {
            auth.signOut();
            gsc.signOut().addOnCompleteListener(getActivity(), task -> {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            });
        }
    }