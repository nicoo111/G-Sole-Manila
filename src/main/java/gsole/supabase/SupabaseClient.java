package gsole.supabase;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SupabaseClient {
    private static final String SUPABASE_URL = "https://tgatznankvpknmnlwuhv.supabase.co";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRnYXR6bmFua3Zwa25tbmx3dWh2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTU5MDg4NDEsImV4cCI6MjA3MTQ4NDg0MX0.1_iEFYBn3UBtUuYOiaTI_6WcD35AYT5mjrLkHzJh3lg";
    private static final String BASE_ENDPOINT = SUPABASE_URL + "/rest/v1/";

    private final OkHttpClient client;

    public SupabaseClient() {
        this.client = new OkHttpClient();
    }

    // ---------- Helper ----------
    private String escapeJson(String value) {
        if (value == null)
            return "null";
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    private Request.Builder baseRequest(String table) {
        return new Request.Builder()
                .url(BASE_ENDPOINT + table)
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_KEY)
                .addHeader("Content-Type", "application/json");
    }

    private String executeRequest(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body() != null ? response.body().string() : "";
        }
    }

    // ---------- Categories (Read only) ----------
    public String getCategories() throws IOException {
        Request request = baseRequest("categories?select=id,name").get().build();
        return executeRequest(request);
    }

    // ---------- Customers ----------
    public String getCustomers() throws IOException {
        Request request = baseRequest("customers").get().build();
        return executeRequest(request);
    }

    public String insertCustomer(String fullName) throws IOException {
        String json = "{\"full_name\":" + escapeJson(fullName) + "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = baseRequest("customers").post(body).build();
        return executeRequest(request);
    }

    public String updateCustomer(int id, String fullName) throws IOException {
        String json = "{\"full_name\":" + escapeJson(fullName) + "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = baseRequest("customers?id=eq." + id).patch(body).build();
        return executeRequest(request);
    }

    public String deleteCustomer(int id) throws IOException {
        Request request = baseRequest("customers?id=eq." + id).delete().build();
        return executeRequest(request);
    }

    // ---------- Reservations ----------
    public String getReservations() throws IOException {
        Request request = baseRequest("reservations").get().build();
        return executeRequest(request);
    }

    public String insertReservation(String reservedAt, int itemId, int customerId, double partialPayment,
            boolean isFullyPaid) throws IOException {
        String json = "{" +
                "\"reserved_at\":" + escapeJson(reservedAt) + "," +
                "\"item_id\":" + itemId + "," +
                "\"customer_id\":" + customerId + "," +
                "\"partial_payment\":" + partialPayment + "," +
                "\"is_fully_paid\":" + isFullyPaid +
                "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = baseRequest("reservations").post(body).build();
        return executeRequest(request);
    }

    public String updateReservation(int id, double partialPayment, boolean isFullyPaid) throws IOException {
        String json = "{" +
                "\"partial_payment\":" + partialPayment + "," +
                "\"is_fully_paid\":" + isFullyPaid +
                "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = baseRequest("reservations?id=eq." + id).patch(body).build();
        return executeRequest(request);
    }

    public String deleteReservation(int id) throws IOException {
        Request request = baseRequest("reservations?id=eq." + id).delete().build();
        return executeRequest(request);
    }

    // ---------- Items ----------
    public String getItems() throws IOException {
        Request request = baseRequest("items").get().build();
        return executeRequest(request);
    }

    public String insertItem(String name, String condition, int size, int categoryId,
            double buyPrice, double sellPrice, String status, boolean isPaid,
            String mop, String dateAdded, String dateSold, double profit) throws IOException {
        String json = "{" +
                "\"name\":" + escapeJson(name) + "," +
                "\"condition\":" + escapeJson(condition) + "," +
                "\"size\":" + size + "," +
                "\"category_id\":" + categoryId + "," +
                "\"buy_price\":" + buyPrice + "," +
                "\"sell_price\":" + sellPrice + "," +
                "\"status\":" + escapeJson(status) + "," +
                "\"is_paid\":" + isPaid + "," +
                "\"mop\":" + escapeJson(mop) + "," +
                "\"date_added\":" + escapeJson(dateAdded) + "," +
                "\"date_sold\":" + escapeJson(dateSold) + "," +
                "\"profit\":" + profit +
                "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = baseRequest("items").post(body).build();
        return executeRequest(request);
    }

    public String updateItem(int id, String status, boolean isPaid, String mop, String dateSold, double profit)
            throws IOException {
        String json = "{" +
                "\"status\":" + escapeJson(status) + "," +
                "\"is_paid\":" + isPaid + "," +
                "\"mop\":" + escapeJson(mop) + "," +
                "\"date_sold\":" + escapeJson(dateSold) + "," +
                "\"profit\":" + profit +
                "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = baseRequest("items?id=eq." + id).patch(body).build();
        return executeRequest(request);
    }

    public String deleteItem(int id) throws IOException {
        Request request = baseRequest("items?id=eq." + id).delete().build();
        return executeRequest(request);
    }

    // ---------- Sales ----------
    public String getSales() throws IOException {
        Request request = baseRequest("sales").get().build();
        return executeRequest(request);
    }

    public String insertSale(int itemId, double amountPaid, String mop) throws IOException {
        String json = "{" +
                "\"item_id\":" + itemId + "," +
                "\"amount_paid\":" + amountPaid + "," +
                "\"mop\":" + escapeJson(mop) +
                "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = baseRequest("sales").post(body).build();
        return executeRequest(request);
    }
}
