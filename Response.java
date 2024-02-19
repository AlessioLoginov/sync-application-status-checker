public sealed
interface Response
permits Response.Success,Response.RetryAfter,Response.Failure
{

    record Success(String applicationStatus, String applicationId) implements Response {}

    record RetryAfter(java.time.Duration delay) implements Response {}

    record Failure(Throwable ex) implements Response {}
}
