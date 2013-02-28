package edu.umn.contactviewer;

import edu.umn.contactviewer.data.ContactProvider;
import edu.umn.contactviewer.data.ContactViewerDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.edit);
	ToolbarConfig toolbar = new ToolbarConfig(this, "Edit Contact");
	
	final EditText editTextName = (EditText) findViewById(R.id.edit_name);
	final EditText editTextPhone = (EditText) findViewById(R.id.edit_phone);
	final EditText editTextTitle = (EditText) findViewById(R.id.edit_title);
	final EditText editTextEmail = (EditText) findViewById(R.id.edit_email);
	final EditText editTextTwitterId = (EditText) findViewById(R.id.edit_twitterId);

	    // setup the save button
	    Button button = toolbar.getToolbarRightButton();
	    button.setText("Save");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Toast.makeText(DetailsActivity.this, "This is a sample application made for SENG 5199-1 in the MSSE program.", Toast.LENGTH_LONG).show();
				ContentValues cUpdateValues = new ContentValues();
				cUpdateValues.put(ContactViewerDatabase.COL_NAME, editTextName.getText().toString());
				cUpdateValues.put(ContactViewerDatabase.COL_PHONE, editTextPhone.getText().toString());
				cUpdateValues.put(ContactViewerDatabase.COL_TITLE, editTextTitle.getText().toString());
				cUpdateValues.put(ContactViewerDatabase.COL_EMAIL, editTextEmail.getText().toString());
				cUpdateValues.put(ContactViewerDatabase.COL_TWITTERID, editTextTwitterId.getText().toString());
				
				getContentResolver().update(ContactProvider.CONTENT_URI, cUpdateValues, ContactViewerDatabase.ID + " = ?", new String[]{getIntent().getStringExtra("contactId")});
			
				String projection[] = { ContactViewerDatabase.ID, ContactViewerDatabase.COL_NAME,  ContactViewerDatabase.COL_PHONE, ContactViewerDatabase.COL_TITLE, ContactViewerDatabase.COL_EMAIL, ContactViewerDatabase.COL_TWITTERID};
        	    Cursor contactCursor = getContentResolver().query(
        	           ContactProvider.CONTENT_URI,
        	              projection, ContactViewerDatabase.ID + " = ?", new String[]{getIntent().getStringExtra("contactId")}, null);
        	    if (contactCursor.moveToFirst()) {
					Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
					
					String contactId = contactCursor.getString(0);
					String name = contactCursor.getString(1);
					String phone = contactCursor.getString(2);
					String title = contactCursor.getString(3);
					String email = contactCursor.getString(4);
					String twitterId = contactCursor.getString(5);
					
					intent.putExtra("contactId", contactId);
					intent.putExtra("name", name);
			    	intent.putExtra("phone", phone);
			    	intent.putExtra("title", title);
			    	intent.putExtra("email", email);
			    	intent.putExtra("twitterId", twitterId);
			    	setResult(Activity.RESULT_OK, intent);
        	    }
        	    finish();
			}
		});
	
	
	String name = getIntent().getStringExtra("name");
	editTextName.setText(name);
	
	String phone = getIntent().getStringExtra("phone");
	editTextPhone.setText(phone);
	
	String title = getIntent().getStringExtra("title");
	editTextTitle.setText(title);
	
	String email = getIntent().getStringExtra("email");
	editTextEmail.setText(email);
	
	String twitterId = getIntent().getStringExtra("twitterId");
	editTextTwitterId.setText(twitterId);
	}
}
