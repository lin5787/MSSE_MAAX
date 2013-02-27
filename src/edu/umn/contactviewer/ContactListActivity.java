package edu.umn.contactviewer;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import edu.umn.contactviewer.data.ContactProvider;
import edu.umn.contactviewer.data.ContactViewerDatabase;

/** Displays a list of contacts.
 *
 */
public class ContactListActivity extends ListActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ToolbarConfig toolbar = new ToolbarConfig(this, "Contacts");

	    // setup the about button
	    Button button = toolbar.getToolbarRightButton();
	    button.setText("About");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(ContactListActivity.this, "This is a sample application made for SENG 5199-1 in the MSSE program.", Toast.LENGTH_LONG).show();
			}
		});
        
		String[] projection = { ContactViewerDatabase.ID, ContactViewerDatabase.COL_NAME, ContactViewerDatabase.COL_PHONE, ContactViewerDatabase.COL_TITLE};
	    String[] uiBindFrom = { ContactViewerDatabase.COL_NAME, ContactViewerDatabase.COL_PHONE, ContactViewerDatabase.COL_TITLE};
	    int[] uiBindTo = { R.id.item_name, R.id.item_phone, R.id.item_title };
	    
	    Cursor contacts = managedQuery(
	            ContactProvider.CONTENT_URI, projection, null, null, null);
	    CursorAdapter adapter = new ContactAdapter(getApplicationContext(), R.layout.list_item, contacts,
	            uiBindFrom, uiBindTo);
        
	    // initialize the list view
	    setListAdapter(adapter);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        // handle the item click events
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show a toast with the TextView text
				//Toast.makeText(getApplicationContext(), 
					//"Clicked: " + ((ContactAdapter)getListAdapter()).getItem(position).getName(),
					//Toast.LENGTH_SHORT).show();
        		
        		String projection[] = { ContactViewerDatabase.ID, ContactViewerDatabase.COL_NAME,  ContactViewerDatabase.COL_PHONE, ContactViewerDatabase.COL_TITLE, ContactViewerDatabase.COL_EMAIL, ContactViewerDatabase.COL_TWITTERID};
        	    Cursor contactCursor = getContentResolver().query(
        	            Uri.withAppendedPath(ContactProvider.CONTENT_URI,
        	                    String.valueOf(id)), projection, null, null, null);
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
				    	startActivity(intent);
        	    	}
        	    contactCursor.close();
				}
			}
        );
        
    }
    
	/* We need to provide a custom adapter in order to use a custom list item view.
	 */
	public class ContactAdapter extends SimpleCursorAdapter {
	
		public ContactAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
		{
			super(context, layout, c, from, to);
		}
	
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View item = inflater.inflate(R.layout.list_item, parent, false);
			return item;
		}
	}
 }


