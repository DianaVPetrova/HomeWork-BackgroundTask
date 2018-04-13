package diana.softuni.bg.andoriddesignpatternshomework.api;


import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Rain {

    @SerializedName("3h")
    private double h3;

    public String getH3() {

        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        //System.out.println(decimalFormat.format(h3));
        return (decimalFormat.format(h3));
    }
}





