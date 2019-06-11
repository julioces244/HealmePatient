package com.tecsup.apaza.healmepaciente;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
//import com.stripe.android.R;
import static java.security.AccessController.getContext;
//import com.stripe.android.controller.ErrorDialogHandler;


public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
            //mErrorDialogHandler.showError("Invalid Card Data");
        }

/*
        Stripe stripe = new Stripe(this, "pk_test_VNUCHfz6K4XFesuJSHCXvcAE00M8vZWR5T");
        stripe.createToken(
                cardToSave,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                    }

                    public void onError(Exception error) {
                        // Show localized error message
                        //Toast.makeText(getContext(),
                         //       error.getLocalizedString(getContext()),
                         //       Toast.LENGTH_LONG
                       // ).show();
                    }
                }
                );

*/

    }
}
