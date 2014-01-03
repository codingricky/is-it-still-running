package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.Result;

import static play.libs.F.Function;
import static play.libs.F.Promise;

public class CheckController extends Controller {

    public static Promise<Result> check() {
        JsonNode json = request().body().asJson();
        String url = json.get("url").asText();
        final Promise<Result> resultPromise = WS.url(url).get().map(
                new Function<WS.Response, Result>() {
                    public Result apply(play.libs.WS.Response response) {
                        int status = response.getStatus();
                        ObjectNode result = Json.newObject();
                        result.put("status", status);
                        return ok(result);
                    }
                }
        );

        return resultPromise;
    }
}
