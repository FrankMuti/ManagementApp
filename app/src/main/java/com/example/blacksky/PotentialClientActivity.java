package com.example.blacksky;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datastructures.AttendedClientsData;
import com.example.blacksky.datastructures.ConfirmedClientsData;
import com.example.blacksky.datastructures.ExpenseData;
import com.example.blacksky.datastructures.ListAdapter;
import com.example.blacksky.datastructures.PotentialClientsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PotentialClientActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<HashMap<String, String>> clientList;

    ExpandableListView expandableListView;
    HashMap<String, List<String>> listChild;
    List<String> listHeader;
    ListAdapter listAdapter;

    TextView childItem;

    MenuItem menuItemDelete;

    private Button promoteUserBtn;
    private EditText promoteUserEdit;

    private FloatingActionButton fabPromote;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potential_client);

        toolbar = findViewById(R.id.listToolbar);
        toolbar.setTitle("Potential Clients");
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle);
        toolbar.setTitleTextColor(Color.parseColor("#f3f3f3"));

       // toolbar.setBackgroundColor(getResources().getColor(R.color.darkText));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.darkText));
        }

        childItem = findViewById(R.id.childItem);
        expandableListView = findViewById(R.id.expListView);
        listChild = PotentialClientsData.getData(getApplicationContext());
        listHeader = new ArrayList<>(listChild.keySet());
        listAdapter = new ListAdapter(getApplicationContext(), listHeader, listChild);
        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                try {
                    Toast.makeText(getApplicationContext(), listAdapter.getChild(groupPosition, childPosition).toString(), Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });



//        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                return false;
//            }
//        });

        //registerForContextMenu(expandableListView);

        fabPromote =  findViewById(R.id.fabPromote);
        fabPromote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                //View promtView = layoutInflater.inflate(R.layout.custom_dialog, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(PotentialClientActivity.this);

                alert.setTitle("Enter Details");

                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText input = new EditText(PotentialClientActivity.this);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        3
//                );
                input.setHint("Name");

                //input.setLayoutParams(lp);
                //alert.setView(input);
                layout.addView(input);

                final EditText input2 = new EditText(PotentialClientActivity.this);
                input2.setHint("Agreed Amount");
                //input2.setLayoutParams(lp);
                //alert.setView(input2);
                layout.addView(input2);

                final EditText input3 = new EditText(PotentialClientActivity.this);
                input3.setHint("Deposit Amount");
                //input2.setLayoutParams(lp);
                //alert.setView(input3);
                layout.addView(input3);
                //final EditText userInput = (EditText) promtView.findViewById(R.id.editTextDialogUserInput);

                alert.setView(layout);
                alert.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDb = new DatabaseHelper(getApplicationContext());
                                confirmClient(input.getText().toString(), input2.getText().toString(), input3.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alert.show();
            }
        });
    }

    private void confirmClient(String cName,String cAgreedAmount, String cDeposit) {
        listChild = PotentialClientsData.getData(PotentialClientActivity.this);
        if (!listChild.containsKey(cName)){
            Toast.makeText(PotentialClientActivity.this, "No Such Client", Toast.LENGTH_SHORT).show();
            return;
        }

        String cPhone = listChild.get(cName).get(0);
        String cLocation = listChild.get(cName).get(1);
        String cService = listChild.get(cName).get(2);
        String cDate = listChild.get(cName).get(3);
        String cTime = listChild.get(cName).get(4);

        boolean add = myDb.insertCCData(cName, cPhone, cLocation, cService, cDate, cTime, cAgreedAmount, cDeposit);

        if (add){
            Toast.makeText(PotentialClientActivity.this, "Potential Client Inserted", Toast.LENGTH_SHORT).show();
            listChild.get(cName).clear();
            myDb.deleteData(cName, DatabaseHelper.PC_TABLE);
            Toast.makeText(PotentialClientActivity.this, "Done", Toast.LENGTH_SHORT).show();
            AttendedClientsData.getData(PotentialClientActivity.this);
            ConfirmedClientsData.getData(PotentialClientActivity.this);
            ExpenseData.getData(PotentialClientActivity.this);
            PotentialClientsData.getData(PotentialClientActivity.this);
        }
    }

    @Override
    public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose an Option");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Promote");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Delete") {
            int id = expandableListView.getSelectedItemPosition();
            Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        }else if (item.getTitle() == "Promote") {

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItemDelete = menu.findItem(R.id.action_delete);
        menuItemDelete.setVisible(false);

        menuItemDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void showDeleteMenu(boolean show) {
        menuItemDelete.setVisible(show);
    }

    ///// ====================================================== /////
    ///// ===============  JSON PARSING DATA  ================== /////
    ///// ====================================================== /////

//
//    private void beginJsonParsing() {
//        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
//        try{
//            JSONObject reader = new JSONObject(loadJsonFromAsset());
//            JSONArray jArray = reader.getJSONArray("potential");
//            for (int i = 0; i < jArray.length(); i++){
//                try{
//                    JSONObject obj = jArray.getJSONObject(i);
//                    // pulling items
//                    String name = obj.getString("Name");
//                    String phone = obj.getString("Phone");
//                    String service = obj.getString("Service");
//                    String date = obj.getString("Date");
//                    addValuesToHashMap(name, phone, service, date);
//                }catch (JSONException e){
//                    e.printStackTrace();
//                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//            ListView lv = findViewById(R.id.lvJson);
//            ListAdapter adapter = new SimpleAdapter(PotentialClientActivity.this, clientList, R.layout.list_item_potential,
//                    new String[]{"Name", "Phone", "Service", "Date"}, new int[] {R.id.idName, R.id.idPhone, R.id.idService, R.id.idDate});
//            lv.setAdapter(adapter);
//        } catch (JSONException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT ).show();
//        }
//    }
//
//    private void addValuesToHashMap(String name, String phone, String service, String date) {
//        HashMap<String, String> client = new HashMap<>();
//
//        // Adding each child node to hashmap
//        client.put("Name", name);
//        client.put("Phone", phone);
//        client.put("Service", service);
//        client.put("Date", date);
//
//        clientList.add(client);
//    }
//
//    private String loadJsonFromAsset() {
//        String json = null;
//        try{
//            InputStream is = this.getAssets().open("database/blacksky.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return json;
//    }

}





























