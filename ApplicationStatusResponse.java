import org.jetbrains.annotations.Nullable;
import java.time.Duration;

public sealed

interface ApplicationStatusResponse
permits ApplicationStatusResponse.Success,ApplicationStatusResponse.Failure
{

    record Success(String id, String status) implements ApplicationStatusResponse {}

    record Failure(@Nullable Duration lastRequestTime, int retriesCount) implements ApplicationStatusResponse {}
}
