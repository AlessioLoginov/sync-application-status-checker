import java.util.concurrent.*;

public class AsyncHandler implements Handler {

    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final Client client;

    public AsyncHandler(Client client) {
        this.client = client;
    }

    @Override
    public ApplicationStatusResponse performOperation(String id) {
        Future<Response> future1 = executor.submit(() -> client.getApplicationStatus1(id));
        Future<Response> future2 = executor.submit(() -> client.getApplicationStatus2(id));

        try {
            while (true) {
                try {
                    Response response = future1.isDone() ? future1.get() : future2.get();
                    if (response instanceof Response.Success) {
                        Response.Success success = (Response.Success) response;
                        return new ApplicationStatusResponse.Success(success.applicationId(),
                                success.applicationStatus());
                    }
                    // Обработка других типов ответов не требуется по заданию
                } catch (ExecutionException | InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ApplicationStatusResponse.Failure(null, 0); // Простая обработка ошибок
                }
            }
        } finally {
            future1.cancel(true);
            future2.cancel(true);
            executor.shutdown();
        }
    }
}
