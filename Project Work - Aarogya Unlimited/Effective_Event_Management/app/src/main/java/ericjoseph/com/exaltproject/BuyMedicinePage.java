package ericjoseph.com.exaltproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BuyMedicinePage extends AppCompatActivity {

    public int quantity = 0;
    public TextView quantityTextView, amount;
    public Bundle medbundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_page);
        quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        amount = (TextView) findViewById(R.id.amount);
        displayPrice(quantity);

        medbundle = getIntent().getExtras();

        TextView medName = (TextView)findViewById(R.id.medName);
        TextView medmfg = (TextView)findViewById(R.id.manu);
        TextView medPres = (TextView)findViewById(R.id.use);
        TextView medQuantity = (TextView)findViewById(R.id.med_quantity);
        TextView medPrice = (TextView)findViewById(R.id.med_price);
        TextView medDescription = (TextView)findViewById(R.id.descript);

        medName.setText(medbundle.getString("Med name"));
        medmfg.setText(medbundle.getString("Med mfd"));
        medPres.setText(medbundle.getString("Med prescription"));


        medPrice.setText(medbundle.getString("Med price"));
        medQuantity.setText(medbundle.getString("Med quantity"));
        medDescription.setText(medbundle.getString("Med description"));

        ImageView medImage = (ImageView)findViewById(R.id.med_img);

        Picasso.get().load(medbundle.getString("Med image")).placeholder(R.drawable.loading_icon).into(medImage);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
        displayPrice(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity>0) {
            quantity = quantity - 1;
            display(quantity);
            displayPrice(quantity);
        }
    }

    private void display(int number) {
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number) {
        int p = quantity * 5;
        amount.setText(String.valueOf(p));
    }
}
