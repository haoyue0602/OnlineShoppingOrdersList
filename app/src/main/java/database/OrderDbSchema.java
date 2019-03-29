package database;

public class OrderDbSchema {
    //define table_name

    public static final class OrderTable{
        public static final String NAME = "orders";

        //define table column 11
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String ORDER_NUMBER = "orderNumber";
            public static final String DATE = "date";
            public static final String FINISHED = "finished";
            public static final String TRACKING_NUMBER = "trackingNumber";
            public static final String COUNT = "count";
            public static final String DESCRIPTION = "description";
            public static final String PRICE ="price";
            public static final String TYPE = "type";
            public static final String SELLER_NAME = "sellerName";
        }
    }

}
