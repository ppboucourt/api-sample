package co.tmunited.bluebook.web.rest.util;

/**
 * Created by adriel on 4/25/17.
 */
public class LongWrapper {
    private Long value;


    public LongWrapper(){
        //
    }
    public LongWrapper(Long value){
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
