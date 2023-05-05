package com.fmt;

import java.util.Comparator;


public class Run {

    public static Comparator<Address> addressComparator = new Comparator<Address>() {
        @Override
        public int compare(Address o1, Address o2) {
            int result = 0;
            result = o1.getZip().compareTo(o2.getZip());
            if (result == 0) {
                result = o1.getState().compareTo(o2.getState());
            }
            return result;
        }
    };

    public static void main(String[] args) {
        // load address from databse 

    

    }
}
