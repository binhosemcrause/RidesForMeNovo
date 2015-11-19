package com.ridesforme.ridesforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ridesforme.ridesforme.adapter.NavigationDrawerAdapter;
import com.ridesforme.ridesforme.basicas.Usuario;
import com.ridesforme.ridesforme.fragments.ContatoFragment;
import com.ridesforme.ridesforme.fragments.MapHomeFragment;

import static com.ridesforme.ridesforme.R.string.closeDrawer;
import static com.ridesforme.ridesforme.R.string.openDrawer;

public class HomeActivity extends AppCompatActivity {
    Usuario mUsuario;

    public String pEndereco;
    public String pNumero;
    public String pCidade;
    public String pBairro;

    Toolbar mToolbar;                                     // Declaring the Toolbar Object
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout mDrawerLayout;                           // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar mDrawerLayout Toggle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = new MapHomeFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, f)
                    .addToBackStack(null)
                    .commit();

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);        // mDrawerLayout object Assigned to the view

        mUsuario = (Usuario) getIntent().getSerializableExtra("usuario");

        initNavigationDrawer();
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
        if (f instanceof MapHomeFragment){
            new MaterialDialog.Builder(this)
                    .title(R.string.exit_dialog_title)
                    .content(R.string.exit_dialog_content)
                    .iconRes(R.drawable.marker)
                    .positiveText(R.string.exit_dialog_agree)
                    .negativeText(R.string.exit_dialog_desagree)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            finish();
                        }
                    })
                    .show();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initNavigationDrawer() {
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        String TITLES[] = {"Home","Oferecer Carona","Perfil","Contato"};
        int ICONS[] = {R.drawable.ic_home,R.drawable.ic_carona,R.drawable.ic_perfil,R.drawable.ic_contato};
        String NAME = mUsuario.getNome();
        String EMAIL = mUsuario.getEmail();
        String PROFILE = "http://i.imgur.com/DvpvklR.png"; //mUsuario.getImagemPerfilUrl();

        mAdapter = new NavigationDrawerAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE, HomeActivity.this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        final GestureDetector mGestureDetector = new GestureDetector(HomeActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mDrawerLayout.closeDrawers();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment f;
                    int position = recyclerView.getChildPosition(child) - 1;
                    if (position == 0) {
                        f = new MapHomeFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, f)
                                .addToBackStack(null)
                                .commit();
                    } else if (position == 1) {
                        Intent it = new Intent(getApplicationContext(), CaronaPasso1Activity.class);
                        it.putExtra("endereco", pEndereco);
                        it.putExtra("numero", pNumero);
                        it.putExtra("cidade", pCidade);
                        it.putExtra("bairro", pBairro);
                        startActivity(it);
                    } else if (position == 2) {
                        Intent it = new Intent(getApplicationContext(), PerfilActivity.class);
                        it.putExtra("usuario", mUsuario);
                        startActivity(it);
                    } else if (position == 3) {
                        f = new ContatoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, f)
                                .addToBackStack(null)
                                .commit();
                    }
                    return true;

                }

                return false;
            }


            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mLayoutManager = new LinearLayoutManager(HomeActivity.this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, mToolbar, openDrawer, closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // mDrawerLayout Toggle Object Made
        mDrawerLayout.setDrawerListener(mDrawerToggle); // mDrawerLayout Listener set to the mDrawerLayout toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }
}
