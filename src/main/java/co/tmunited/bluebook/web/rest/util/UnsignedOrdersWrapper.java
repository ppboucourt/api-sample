package co.tmunited.bluebook.web.rest.util;

/**
 * Created by adriel on 7/8/17.
 */
public class UnsignedOrdersWrapper {

    private int unsignedOrdersByDoctor = 0;
    private int unsignedOrders= 0;

    public int getUnsignedOrdersByDoctor() {
        return unsignedOrdersByDoctor;
    }

    public void setUnsignedOrdersByDoctor(int unsignedOrdersByDoctor) {
        this.unsignedOrdersByDoctor = unsignedOrdersByDoctor;
    }

    public int getUnsignedOrders() {
        return unsignedOrders;
    }

    public void setUnsignedOrders(int unsignedOrders) {
        this.unsignedOrders = unsignedOrders;
    }
}
