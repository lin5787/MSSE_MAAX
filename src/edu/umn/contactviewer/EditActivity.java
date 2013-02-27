package edu.umn.contactviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.edit);
	ToolbarConfig toolbar = new ToolbarConfig(this, "Edit Contact");

	    // setup the about button
	    Button button = toolbar.getToolbarRightButton();
	    button.setText("Save");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Toast.makeText(DetailsActivity.this, "This is a sample application made for SENG 5199-1 in the MSSE program.", Toast.LENGTH_LONG).show();
				
			}
		});
	
	EditText editTextName = (EditText) findViewById(R.id.edit_name);
	String name = getIntent().getStringExtra("name");
	editTextName.setText(name);
	
	EditText editTextPhone = (EditText) findViewById(R.id.edit_phone);
	String phone = getIntent().getStringExtra("phone");
	editTextPhone.setText(phone);
	
	EditText editTextTitle = (EditText) findViewById(R.id.edit_title);
	String title = getIntent().getStringExtra("title");
	editTextTitle.setText(title);
	
	EditText editTextEmail = (EditText) findViewById(R.id.edit_email);
	String email = getIntent().getStringExtra("email");
	editTextEmail.setText(email);
	
	EditText editTextTwitterId = (EditText) findViewById(R.id.edit_twitterId);
	String twitterId = getIntent().getStringExtra("twitterId");
	editTextTwitterId.setText(twitterId);
	}
}
