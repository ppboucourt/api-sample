package co.tmunited.bluebook.web.rest.util;

/**
 * Created by adriel on 4/25/17.
 */
public class BooleanWrapper {
    private Boolean value;


    public BooleanWrapper(){
        //
    }
    public BooleanWrapper(Boolean value){
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
