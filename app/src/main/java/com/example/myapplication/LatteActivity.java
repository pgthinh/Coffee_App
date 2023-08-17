package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.OrderContract;

public class LatteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ImageView imageView, cart,back;
    ImageButton plusquantity, minusquantity;
    TextView quantitynumber, drinnkName, coffeePrice;
    CheckBox upSize, doubleShot;
    Button addtoCart;
    int quantity = 0;
    public Uri mCurrentCartUri;
    boolean hasAllRequiredValues = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = findViewById(R.id.imageViewInfo);
        plusquantity = findViewById(R.id.addquantity);
        minusquantity = findViewById(R.id.subquantity);
        quantitynumber = findViewById(R.id.quantity);
        drinnkName = findViewById(R.id.drinkNameinInfo);
        coffeePrice = findViewById(R.id.totalPrice);
        upSize = findViewById(R.id.upSize);
        addtoCart = findViewById(R.id.addtocart);
        doubleShot = findViewById(R.id.doubleShot);
        cart = findViewById(R.id.cart);
        back = findViewById(R.id.back);
        drinnkName.setText("Latte");
        imageView.setImageResource(R.drawable.latte);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatteActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatteActivity.this, SummaryActivity.class);
                startActivity(intent);
                SaveCart();
            }
        });

        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int basePrice = 35000;
                quantity++;
                displayQuantity();
                int coffePrice = basePrice * quantity;
                String setnewPrice = String.valueOf(coffePrice);
                coffeePrice.setText(setnewPrice + " VND");

                int ifCheckBox = CalculatePrice(upSize, doubleShot);
                coffeePrice.setText(+ifCheckBox + " VND");
            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int basePrice = 35000;

                if (quantity == 0) {
                    Toast.makeText(LatteActivity.this, "Must equal or larger than 0", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                    int coffePrice = basePrice * quantity;
                    String setnewPrice = String.valueOf(coffePrice);
                    coffeePrice.setText(setnewPrice + " VND");

                    int ifCheckBox = CalculatePrice(upSize, doubleShot);
                    coffeePrice.setText(+ifCheckBox + " VND");
                }
            }
        });

        doubleShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ifCheckBox = CalculatePrice(upSize, doubleShot);
                coffeePrice.setText(+ifCheckBox + " VND");
            }
        });

        upSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ifCheckBox = CalculatePrice(upSize, doubleShot);
                coffeePrice.setText(+ifCheckBox + " VND");
            }
        });

    }
    private int CalculatePrice(CheckBox upSize, CheckBox doubleShot) {
        int basePrice = 35000;
        if (upSize.isChecked())
            basePrice = basePrice + 10000;
        if (doubleShot.isChecked())
            basePrice = basePrice + 10000;
        return  basePrice*quantity;
    }

    private boolean SaveCart() {
        hasAllRequiredValues=false;
        String name = drinnkName.getText().toString();
        String price = coffeePrice.getText().toString();
        String quantity = quantitynumber.getText().toString();

        if (Integer.parseInt(quantity) > 0)
        {
            ContentValues values = new ContentValues();
            values.put(OrderContract.OrderEntry.COLUMN_NAME, name);
            values.put(OrderContract.OrderEntry.COLUMN_PRICE, price);
            values.put(OrderContract.OrderEntry.COLUMN_QUANTITY, quantity);

            if (doubleShot.isChecked()) {
                values.put(OrderContract.OrderEntry.COLUMN_DOUBLESHOT, "Double shot: YES");
            } else {
                values.put(OrderContract.OrderEntry.COLUMN_DOUBLESHOT, "Double shot: NO");
            }

            if (upSize.isChecked()) {
                values.put(OrderContract.OrderEntry.COLUMN_UPSIZE, "Upsize: YES");
            } else {
                values.put(OrderContract.OrderEntry.COLUMN_UPSIZE, "Upsize: NO");
            }

            if (mCurrentCartUri == null) {
                Uri newUri = getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI, values);
                if (newUri == null) {
                    Toast.makeText(this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Success  adding to Cart", Toast.LENGTH_SHORT).show();

                }
            }

            hasAllRequiredValues = true;

        }
        return hasAllRequiredValues;
    }

    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {OrderContract.OrderEntry._ID,
                OrderContract.OrderEntry.COLUMN_NAME,
                OrderContract.OrderEntry.COLUMN_PRICE,
                OrderContract.OrderEntry.COLUMN_QUANTITY,
                OrderContract.OrderEntry.COLUMN_DOUBLESHOT,
                OrderContract.OrderEntry.COLUMN_UPSIZE
        };

        return new CursorLoader(this, mCurrentCartUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME);
            int price = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE);
            int quantity = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY);
            int doubleshot  = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_DOUBLESHOT);
            int upsize = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_UPSIZE);


            String nameofdrink = cursor.getString(name);
            String priceofdrink = cursor.getString(price);
            String quantityofdrink = cursor.getString(quantity);
            String yeshasDoubleshot = cursor.getString(doubleshot);
            String yeshasUpsize = cursor.getString(upsize);

            drinnkName.setText(nameofdrink);
            coffeePrice.setText(priceofdrink);
            quantitynumber.setText(quantityofdrink);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        drinnkName.setText("");
        coffeePrice.setText("");
        quantitynumber.setText("");

    }
}
