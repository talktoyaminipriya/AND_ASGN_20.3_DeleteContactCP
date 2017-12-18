package com.example.priya.deletecontactcp;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText mname,mphone;
    Button mdeletecontact,mview;
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//calling views
        
        mname = (EditText)findViewById(R.id.name);
        mphone = (EditText)findViewById(R.id.phone);
        mdeletecontact = (Button) findViewById(R.id.deletecontact);

        mdeletecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting values from edit text
                String name = mname.getText().toString();

                //validating if edit text has text or balnk
                if(name.equals("")){

                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                    return;


                }
            }
        });
    }

public void deleteContact(String name){
    
    // getting content resolver to get data from content provider

    ContentResolver cr = getContentResolver();
    String where = ContactsContract.Data.DISPLAY_NAME+" = ? ";
    String[] params = new String[] {name};

    // Creates a new array of ContentProviderOperation objects.
    ArrayList<ContentProviderOperation> ops =
            new ArrayList<ContentProviderOperation>();

    // Builds the operation and delete it to the array of operations
    ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
            .withSelection(where,params)

                       // Builds the operation and adds it to the array of operations
            .build());
    //Applies the array of ContentProviderOperation objects in batch. The results are discarded.
    try {
        cr.applyBatch(ContactsContract.AUTHORITY, ops);
        Toast.makeText(getBaseContext(), "Contacts deleted sucessfully", Toast.LENGTH_SHORT).show();
    } catch (RemoteException e) {
        e.printStackTrace();
    } catch (OperationApplicationException e) {
        e.printStackTrace();
    }

    Toast.makeText(this,"Contact deleted"+name,Toast.LENGTH_SHORT).show();

}
}
